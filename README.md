# 美食图鉴 (FoodAtlas)

FoodAtlas 是一款旨在呈现"传家宝日志"质感的美食食谱应用。本项目严格按照设计规范实现，采用了经典、优雅的排版风格及配色。

## 🛠 技术栈
* **架构**: Clean Architecture 多模块化 (Multi-Module) 结构 (`:app`, `:core:designsystem`, `:feature:home`)
* **界面框架**: Jetpack Compose (Material 3)
* **图片加载**: Coil (AsyncImage)
* **版本控制**: Git (遵循 Conventional Commits)

## 🎯 当前开发进度

本项目分阶段稳步推进：

### Phase 1: 建立核心设计系统
* 配置项目骨架。
* 构建 `:core:designsystem` 独立模块，包含颜色 (Color)、字体排版 (Typography)、阴影拓展 (`ambientShadow()`) 规范。

### Phase 2-4: 高保真首页 UI
* 引入高频基础组件 (SearchBar, NavigationBar, Chips)。
* 使用了特殊的“双层毛玻璃”视觉设计（顶层 `blur` 状态覆盖在下方的列表上）。
* `feature:home` 界面实现 2 列网格呈现菜品数据，通过分类标签实现实时展示筛选。
* 成功加载由设计稿生成的中文化假数据（Fake Recipes）。

### Phase 5: 食谱详情页
* 实现从首页食谱卡片点击向单个食谱详细页面的导航拦截 (内存状态路由)。
* 构建食谱的完整内容：大图页眉 (Hero Image)、多维度统计数据卡（累计制作/距上次制作记录）、斑马线式食材清单、圆形计步做法列表。
* 处理 Android 系统返回手势 (`BackHandler`) 防止异常退出。

### Phase 6: 标准 Navigation 系统接入
* 移除简陋的状态栏控制机制，正式加载官方级 `androidx.navigation.compose` 依赖。
* **动效平滑**：自定义了横向 `slideIntoContainer` 及 `fadeOut` 的丝滑原生长场动画。
* **数据纯净**：实现了将高内存的序列化数据剔除出页面跳转之间，仅通过纯 String Type 的 ID 找到下一个单页状态。

## 🚀 未来规划
* **Phase 7**: 构建悬浮按钮“添加食谱”组件或扩展底部四个页签 (Discover 等) 以及整合 `ViewModel层` 数据。
* **后继规划**: 建立完整的数据库持久化 (Room DB)、本地配置 (DataStore)。
