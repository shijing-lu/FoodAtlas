# 美食图鉴 (Food Atlas) - Android Compose 架构与技术设计文档

基于项目 **美食图鉴首页 (ID: 11571656404512397612)** 的设计规范《The Culinary Handbook》，本文档重点规划如何在 Android 端基于 Jetpack Compose 打造高内聚、低耦合、符合现代化 Android 行业最佳实践的架构体系。

---

## 1. 核心架构设计理念 (Architecture Philosophy)

采用 **Clean Architecture (整洁架构)** 思想，融合目前 Google Android 推荐的强范式：**单向数据流 (UDF, Unidirectional Data Flow)** 与 **MVI (Model-View-Intent)** 表现层模式。
此架构将分离业务逻辑与 UI 渲染，通过单一可信数据源 (Single Source of Truth) 来提升应用稳定性，特别适合以展示、交互为主且视觉要求极高（如纸质质感、柔和阴影映射）的美食应用。

---

## 2. 技术栈选型基准 (Tech Stack)

| 层次 | 技术 | 版本/备注 |
|------|------|-----------|
| **UI** | Jetpack Compose + Material 3 | BOM 2024.12.01 |
| **状态管理** | ViewModel + `StateFlow<UiState>` | lifecycle-viewmodel-compose |
| **导航** | Jetpack Navigation Compose | 2.7.7 |
| **本地持久化** | Room | 2.6.1 |
| **图片加载** | Coil Compose | coil 3.x |
| **协程 & 流** | Kotlin Coroutines + Kotlin Flow | — |
| **依赖注入** | 手动 DI (Phase 9 前) → 计划迁移 Hilt | — |
| **KSP** | Kotlin Symbol Processing | 2.0.0-1.0.21 |

> ⚠️ **已实现** vs **计划中**：Hilt、Retrofit、DataStore 为已规划但暂未实施项目。

---

## 3. 模块化拆分方案 (Modularization Design)

当前**已实施**的模块结构（`*` = 已完全实现）：

```text
├── app/                   * 顶层壳：NavHost、MainActivity、DI 入口
├── feature/
│   └── home/              * 首页 + 添加食谱 + 详情页（HomeScreen / AddRecipeScreen / RecipeDetailScreen）
│                            包含 HomeViewModel、HomeUiState
├── core/
│   ├── designsystem/      * 颜色系统、Material3 主题、Typography、ambientShadow Modifier
│   ├── model/             * 共享数据模型 (Recipe, Ingredient)
│   ├── database/          * Room 数据库（实体/DAO/数据库/Mapper）
│   └── data/              * Repository 层（暴露 Flow<List<Recipe>>）
```

**依赖关系图**：
```
app  →  feature:home
app  →  core:model
feature:home  →  core:designsystem
feature:home  →  core:model
feature:home  →  core:data
core:data     →  core:database
core:data     →  core:model
core:database →  core:model
```

---

## 4. 重点突破：UI 与设计系统落地设计 (`:core:designsystem`)

本项目在 StitchMCP 中明确定义了 *The Living Heirloom* (传家宝) 的艺术取向，在 Compose 中我们将放弃部分 Material 的默认视觉，进行深度重构：

**一、 The "No-Line" Rule (无边框阴影律)**
我们将禁用系统默认的 `Divider` 与普通边框，而在 Compose Theme 中建立层级映射：
- `SurfaceLevel0`: `surface` (`#fbf9f5`)
- `SurfaceLevel1`: `surface-container-low` (`#f5f3ef`) 
- `SurfaceLevel2`: `surface-container-lowest` (`#ffffff`) 
通过颜色的阶梯变化而非 1dp 边框来实现板块分离。

**二、 Typography (版式)**
建立两套自定义字体族：
- **Noto Serif (显示类/Headline)**：用作《手册》感的大字标题。
- **Plus Jakarta Sans (正文/Label)**：用作菜谱配料、时间等功能性信息展示。

**三、 专属交互组件**
- 将项目中的 `BoxShadow (0 12px 32px rgba(86, 67, 56, 0.06))` 封装为自定义修饰符 `Modifier.ambientShadow()`。
- 基于 `Brush.linearGradient` 实现 135 度的按钮主色渐变，及毛玻璃 (Glassmorphism) `Modifier.blur()` 状态栏支持。

---

## 5. MVI 数据流向图与机制

本架构中避免了传统 MVVM 多重 LiveData 造成的“状态交错冲突”。一个完整的用户操作流如下：

1. **User Action (View)**: 用户点击 "关注按钮"。
2. **Intent (View -> ViewModel)**: UI 层触发事件 `viewModel.dispatch(RecipeIntent.ToggleFavorite(id))`。
3. **Domain / Data 层 (ViewModel 处理)**: 发起网络请求或写入 Room 缓存，执行耗时操作挂起协程。
4. **Mutate State (State 更新)**: 改变当前唯一数据源实体，通过 `.update { it.copy(...) }` 产出新的不可变 `RecipeUiState`。
5. **Render (Compose)**: Compose 监听到 `uiState` 更改 (通过 `collectAsStateWithLifecycle()`)，重组界面，展示爱心动效。
6. **Side Effect (ViewModel -> View)**: 若发生网络错误，ViewModel 通过 `SharedFlow` 抛起一个 Event，UI 层捕获弹出 Snackbar，因为是 Event，即使用户旋转屏幕也不再重复触发。

---

## 6. Room 数据库设计 (Database Schema)

### 6.1 领域模型 (`core:model`)

外部数据模型，供 UI 层与 ViewModel 使用：

```kotlin
// Recipe.kt
data class Recipe(
    val id: String = "",          // 对应数据库 id.toString()，方便 Nav 传参
    val title: String,             // 菜品名称
    val imageUrl: String,          // 封面图片 URL 或本地 Uri
    val lastCookedInfo: String,    // 显示文字："3天前" / "首次制作" 等
    val category: String,          // 分类标签："家常菜" / "汤品" / "甜品" ...
    val cookTime: String = "",     // 烹饪时长："30分钟"
    val difficulty: String = "",   // 难度："简单" / "中等" / "困难"
    val cookCount: Int = 0,        // 累计制作次数
    val daysSinceLastCook: Int? = null,  // 距上次制作天数，null = 从未制作
    val ingredients: List<Ingredient> = emptyList(),
    val steps: List<String> = emptyList()
)

data class Ingredient(
    val name: String,     // 食材名："西红柿"
    val amount: String    // 用量："2个" / "100g"
)
```

---

### 6.2 数据库表结构

#### 表 1：`recipes`

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | 数据库主键，唯一标识 |
| `title` | `TEXT` | `NOT NULL` | 菜品名称 |
| `imageUrl` | `TEXT` | `NOT NULL` | 封面图 URL / 本地 URI |
| `lastCookedInfo` | `TEXT` | `NOT NULL` | 展示用的「上次制作」字符串 |
| `category` | `TEXT` | `NOT NULL` | 分类标签 |
| `cookTime` | `TEXT` | `NOT NULL` | 烹饪时长字符串 |
| `difficulty` | `TEXT` | `NOT NULL` | 难度字符串 |
| `cookCount` | `INTEGER` | `NOT NULL` | 累计制作次数，默认 0 |
| `daysSinceLastCook` | `INTEGER` | `NULLABLE` | 距上次制作天数，NULL = 从未制作 |

#### 表 2：`ingredients`

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | 主键 |
| `recipeId` | `INTEGER` | `NOT NULL, FK → recipes(id) CASCADE DELETE` | 所属食谱 |
| `name` | `TEXT` | `NOT NULL` | 食材名 |
| `amount` | `TEXT` | `NOT NULL` | 用量描述 |

#### 表 3：`steps`

| 列名 | 类型 | 约束 | 说明 |
|------|------|------|------|
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | 主键 |
| `recipeId` | `INTEGER` | `NOT NULL, FK → recipes(id) CASCADE DELETE` | 所属食谱 |
| `stepDescription` | `TEXT` | `NOT NULL` | 步骤描述文字 |
| `stepOrder` | `INTEGER` | `NOT NULL` | 步骤顺序（从 0 开始） |

> 🔗 **级联删除**：删除 `recipes` 中的一行，对应的所有 `ingredients` 和 `steps` 行自动删除。

---

### 6.3 关系 POJO (`RecipeWithDetails`)

```kotlin
data class RecipeWithDetails(
    @Embedded val recipe: RecipeEntity,
    @Relation(parentColumn = "id", entityColumn = "recipeId")
    val ingredients: List<IngredientEntity>,
    @Relation(parentColumn = "id", entityColumn = "recipeId")
    val steps: List<StepEntity>
)
```
Room 在查询时自动 JOIN 三张表，返回完整食谱数据。

---

### 6.4 DAO 接口 (`RecipeDao`)

| 方法 | 注解 | 返回类型 | 说明 |
|------|------|---------|------|
| `getAllRecipes()` | `@Transaction @Query` | `Flow<List<RecipeWithDetails>>` | 全量订阅，首页实时刷新 |
| `getRecipeById(id)` | `@Transaction @Query` | `Flow<RecipeWithDetails?>` | 详情页订阅 |
| `insertRecipe(entity)` | `@Insert REPLACE` | `Long` (新 ID) | 插入单条食谱头信息 |
| `insertIngredients(list)` | `@Insert REPLACE` | `Unit` | 批量插入食材 |
| `insertSteps(list)` | `@Insert REPLACE` | `Unit` | 批量插入步骤 |
| `insertRecipeWithDetails(...)` | `@Transaction` | `Unit` | **原子性**插入食谱+食材+步骤 |
| `deleteRecipe(entity)` | `@Delete` | `Unit` | 删除食谱（级联清理子表） |

---

### 6.5 数据流向

```
AddRecipeScreen
    │ 用户点击「保存」
    ▼
HomeViewModel.saveRecipe()
    │
    ▼
RecipeRepository.insertRecipe()   ← core:data
    │
    ▼
RecipeDao.insertRecipeWithDetails()  ← core:database (事务)
    │ 写入 Room SQLite
    ▼
RecipeDao.getAllRecipes()             → Flow 自动更新
    │
    ▼
RecipeRepository (Flow 透传)
    │
    ▼
HomeViewModel.uiState (StateFlow<HomeUiState>)
    │
    ▼
HomeScreen (Compose recompose → 列表更新)
```

---

## 7. 后续实施路径 (Implementation Roadmap)

| Phase | 状态 | 内容 |
|-------|------|------|
| 1 | ✅ 完成 | 多模块骨架 + Design System |
| 2–4 | ✅ 完成 | 首页高保真 UI |
| 5 | ✅ 完成 | 详情页 |
| 6 | ✅ 完成 | Navigation Compose 接入 |
| 7 | ✅ 完成 | AddRecipeScreen UI |
| 8 | ✅ 完成 | Room 数据库骨架 + 模块重构 |
| **9** | 🚧 下一步 | DI 接通 Repository，保存按钮写库，首页切 Room 数据流 |
| 10 | 📋 计划 | 图片选择（相册/拍照） |
| 11 | 📋 计划 | 更新食谱 / 删除食谱功能 |
| 12 | 📋 计划 | Hilt 依赖注入全面接入 |
