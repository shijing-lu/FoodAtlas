# 第六阶段 (Phase 6)：引入标准导航系统 (Navigation Compose)

## 1. 为什么我们需要真正的 Navigation？
在第五阶段，我们使用了一个简单的变量（`selectedRecipe`）来记录大伙切到了哪个页面。在那时我就提过，这只是个占位的替代方案。
**这会有极大的隐患**：假如你按下了 Android 实体返回键，或者页面变多、想要有进入动画的时候，单纯靠一个变量去控制切换是很脆弱且僵硬的，而且一旦系统内存不够回收了 App，你的页面进度就会全部丢失。

因此这阶段，我们动了“大手术”，去掉了这层简陋的控制膜，接入了 Android 官方的现代路由系统：**Jetpack Navigation Compose**。

---

## 2. 本次变更的代码“地图”

| 涉及文件 | 带来的质变 |
|---|---|
| **`/gradle/libs.versions.toml`**<br>**`/app/build.gradle.kts`** | **引入高级依赖**。加入了 `androidx.navigation.compose` 依赖库，这就像是我们引进了专业的“交通指挥员”。 |
| **`/app/.../MainActivity.kt`** | **建立了换乘枢纽 (`NavHost`)**：<br> - 定义了两个站牌(Route)：`"home"` 和 `recipe_detail/{recipeId}`。<br> - `navController` 就是调度中心。当你在首页点击任何卡片时，它不再传递整个繁重的数据体给详情页，而是**只传入一个轻巧的 ID (`recipeId`)**。<br> - 到达详情站后，详情页自己再通过这个 ID 在库里找到需要渲染的所有食材和步骤。 |

---

## 3. 专业级丝滑切换效果 (Transitions)

直接变来变去的画面太生硬了！为了让 App 更有原生的顺滑感，我们在路由构建时专门加了“转场动画”。你现在可以在体验时好好感受下：
```kotlin
// 滑入与淡入的融合 (进入详情时)：从右边优雅地划入画面
enterTransition = {
    slideIntoContainer(
        towards = SlideDirection.Left,
        animationSpec = tween(300)
    ) + fadeIn(tween(300))
}

// 退出时的滑出效果 (离开详情时)：随着划动向右方消失
popExitTransition = {
    slideOutOfContainer(
        towards = SlideDirection.Right,
        animationSpec = tween(300)
    ) + fadeOut(tween(300))
}
```

---

## 4. 本机测试验收建议

这就是一个完整商业应用该有的“页面穿梭”机制。
你可以去真机上：
1. **点进去：** 此时你应该能看到详情页是从右边带点轻巧阻尼过渡进来的。
2. **滑出来：** 在屏幕最左边缘向心划动或者点顶部的按钮，画面会柔和地滑出回到首页。
3. 把 App 切到后台再切回来，它也能精确地记得你在第几层（这就是纯正 BackStack 的功劳）。

**准备开启数据库功能或者新增页面的 Phase 7 了吗？** 我们的代码现在已经被妥善地记录在 Git 系统里了哦！
