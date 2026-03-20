# 美食图鉴 (FoodAtlas) 开发日志

---

## ✅ Phase 1 · 2026-03-20 · 多模块框架 + 设计系统
**目标**: 搭建整洁架构的多模块骨架，将设计系统从业务层剥离。
- 新增 `:core:designsystem` 模块（颜色/字体/圆角/主题/自定义Shadow）
- 配置根目录 `build.gradle.kts`，统一注册 `android.library` 插件以避免版本冲突
- 主模块 `:app` 通过 `project(":core:designsystem")` 引用设计系统
- `MainActivity` 接入 `FoodAtlasTheme`，完全取代官方默认主题
- **Bug Fix**: Gradle `android.library` 未在根目录声明导致报错 → 已加 `apply false` 声明

---

## ✅ Phase 2 · 2026-03-20 · 首页骨架 + 食谱卡片（Demo版）
**目标**: 验证 `:feature:home` 模块通道是否畅通，确保 Coil 图片能加载。
- 新增 `:feature:home` 模块，引入 `coil-compose` 图片加载库
- 写入首个 `HomeScreen`（单列瀑布流）、`RecipeCard`（大图+纸片叠加）、`IngredientChip`
- `AndroidManifest` 加入 `INTERNET` 权限
- 数据使用 `fakeRecipes` 假数据，待后续替换网络层
- **注意**: 此版本内容为 Demo 占位英文数据，正式内容在 Phase 3 替换

---

## ✅ Phase 3 · 2026-03-20 · 接轨 Stitch 设计 + 中文内容 + 底部导航
**目标**: 从 Stitch MCP 获取设计稿真实内容，完全替换 Demo 数据，搭建底部菜单。
- 通过 Stitch MCP 拉取《美食图鉴首页》实际内容（番茄炒蛋/秘制排骨/酸辣汤/浆果蛋糕）
- 全面中文化：食谱名、耗时标签、难度字段
- 新增 `FoodAtlasBottomBar`（Home / Discover / History / Profile）
- 顶部自定义毛玻璃头部（`blur(20dp)` + 80% 透明度）
- **Bug Fix**: 编译缓存错误 `zip-cache 系统找不到指定文件` → `gradlew clean` 解决

---

## ✅ Phase 4 · 2026-03-20 · 对齐 Stitch 设计稿 95% 高保真首页
**目标**: 彻底按设计稿重写首页，从单列瀑布流换成 2 列网格。
- 新增 `HomeComponents.kt`（TopBar汉堡菜单+头像、SearchBar、CategoryFilter、DailyInspirationCard）
- 重写 `RecipeCard` → `RecipeGridCard`（正方形图片+分类角标+标题+时钟时间）
- 新增 `RecipesGrid`（2列网格布局，通过 `chunked(2)` 实现分行）
- `HomeScreen` 全部接入新组件，顶部增加 FAB 橙色 `+` 按钮
- 分类筛选实现：点击标签即时筛选食谱网格（本地假数据级别）
- **Bug Fix**: 顶部"美食图鉴"标题和状态栏重叠 → 在 `HomeTopBar` 加 `.statusBarsPadding()`

---

## ✅ Phase 5 · 2026-03-20 · 菜品详情页 + 页面导航
**目标**: 按照 Stitch 设计稿《菜品详情 - 正面》实现全品质详情页，并接通首页点击跳转。
- **Bug Fix**: 标题与状态栏重叠 → 在 `HomeTopBar` 加 `.statusBarsPadding()` 修复
- `Recipe` 数据模型扩充：加入 `ingredients`（食材列表）和 `steps`（烹饪步骤）
- `fakeRecipes` 为4道菜配上完整食材和步骤数据（中文）
- 新增 `RecipeDetailScreen.kt`：大图头部 + 返回按钮 + 分类角标 + 基本信息 + 食材列表 + 步骤列表
- `MainActivity` 重构为 `FoodAtlasNavigation()`：使用内存状态管理首页↔详情页切换
- `HomeScreen` 接入 `onRecipeClick` 回调，点击网格卡片跳转详情页
- **注**: 目前是简易内存导航；Navigation Compose 正式接入在 Phase 6

---

## ✅ Phase 6 · 2026-03-20 · 引入 Jetpack Navigation Compose 与过渡动画
**目标**: 采用官方标准的 Navigation 系统取代单薄的内存状态路由。
- 引入依赖 `androidx.navigation.compose` (版本 2.7.7) 
- 重构 `MainActivity`，提取出 `FoodAtlasNavHost()` 组件
- 建立标准的 `Route` 常量路由 (`home`, `recipe_detail/{recipeId}`)
- 通过只传递 `recipeId` 给详情页从而实现高内聚和解耦
- 为横向详情页切入加上了自定义动画 (`slideIntoContainer` / `slideOutOfContainer`)
- 卸了自定义在 `RecipeDetailScreen` 内多余的拦截逻辑，让 Navigation 系统纯原生接管系统返回键

---

## ✅ Phase 7 · 2026-03-20 · 添加图鉴页面 (Add Recipe UI)
**目标**: 按照 `DESIGN.md` 设计稿原图还原“添加图鉴”页面的 UI。
- 实现 `AddRecipeScreen.kt`，接入 NavHost (`Route.ADD_RECIPE`)。
- 在 `HomeScreen` 中的 FloatingActionButton (+) 加入相应的跳转方法。
- **无描边规则 (No-Line Rule)** 重现：去除了系统文本框自带底纹，基于自定义圆角色块拼接。对于图片虚线框使用定制 `Canvas` 画布描绘破折虚线。
- 新增组件级抽象：`OrganicTextField` (带焦点色盲切换)、`IngredientEditRow` (多列横排文本框与删除键)、`StepEditCard` (包含步骤序号，内容文本区与配图占位)。
- **交互规范与质感**: 使用环境光阴影 (`ambientShadow`), 点击高亮色彩变化，打造如同羊皮卷轴手账一般的细腻质感。

---

## ✅ Phase 8 · 2026-03-20 · 多模块架构重构 + Room 数据库骨架
**目标**: 引入清晰架构分层，将数据与 UI 完全解耦；建立 Room 数据库结构。
- 新增 3 个纯 Kotlin 模块：`:core:model`、`:core:database`、`:core:data`
- `:core:model` — 共享数据模型 `Recipe` / `Ingredient`（从 `feature:home` 剥离，消灭循环依赖）
- `:core:database` — Room DB 全貌：
  - `RecipeEntity`、`IngredientEntity`、`StepEntity`（含外键 + `CASCADE` 删除）
  - `RecipeWithDetails` 关系 POJO（`@Embedded` + `@Relation`）
  - `RecipeDao`（CRUD + 事务性批量插入）
  - `RecipeDatabase` 单例
  - `RecipeMapper` 扩展函数（Entity ↔ ExternalModel 双向转换）
- `:core:data` — `RecipeRepository`（暴露 `Flow<List<Recipe>>`，隔绝 DAO 与 UI）
- `:feature:home` — `HomeViewModel` 接入 Repository，通过 `StateFlow<HomeUiState>` 驱动界面
- **Bug Fix**: Windows 文件锁导致 Kotlin daemon 崩溃 → `gradlew clean` 解决
- **Bug Fix**: 各新模块 JVM 目标不一致（1.8 vs 21）→ 统一补加 `compileOptions` / `kotlinOptions`
- **Bug Fix**: `compileSdk` 版本不匹配 → 全项目统一为 36
- **Bug Fix**: `Recipe` 移包后各文件漏加 import → 逐一补齐 `core.model` import 声明

---

## 🚧 Phase 9 · 待开发 · 接通数据流 + 保存功能
**计划**:
- 在 `App` 模块提供 `RecipeDatabase` / `RecipeRepository` 单例（Hilt 或手动 DI）
- 将 `HomeScreen` / `HomeViewModel` 从 `fakeRecipes` 切换为 Room 数据流
- 接通 `AddRecipeScreen` 保存按钮，将用户填写的食谱写入数据库
- 首次启动时自动写入示例数据（避免空列表）
