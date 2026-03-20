package com.example.foodatlas.feature.home

import com.example.foodatlas.core.model.Recipe
import com.example.foodatlas.core.model.Ingredient

data class HomeUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val selectedCategory: String = "全部",
    val errorMessage: String? = null
)

val categories = listOf("全部", "肉禽", "蔬菜", "汤品")

val fakeRecipes = listOf(
    Recipe(
        id = "1",
        title = "番茄炒蛋",
        imageUrl = "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400",
        lastCookedInfo = "3天前烹饪过",
        category = "蔬菜",
        cookTime = "15分钟",
        difficulty = "简单",
        cookCount = 12,
        daysSinceLastCook = 3,
        ingredients = listOf(
            Ingredient("鸡蛋", "3 个"), Ingredient("番茄", "2 个"),
            Ingredient("食用油", "适量"), Ingredient("盐", "1 茶匙"),
            Ingredient("白糖", "半茶匙"), Ingredient("葱花", "少许")
        ),
        steps = listOf(
            "番茄洗净后切成滚刀块，备用。",
            "鸡蛋打入碗中，加入少许盐，充分打散成蛋液。",
            "热锅凉油，油热后倒入蛋液，炒至八分熟后盛出备用。",
            "锅中再加少许油，放入番茄块翻炒，加盐和白糖炒出汤汁。",
            "倒入鸡蛋，翻炒均匀，撒上葱花即可出锅。"
        )
    ),
    Recipe(
        id = "2",
        title = "秘制排骨",
        imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?w=400",
        lastCookedInfo = "从未烹饪过",
        category = "肉禽",
        cookTime = "45分钟",
        difficulty = "中等",
        cookCount = 5,
        daysSinceLastCook = null,
        ingredients = listOf(
            Ingredient("猪排骨", "500g"), Ingredient("生抽", "3 汤匙"),
            Ingredient("老抽", "1 汤匙"), Ingredient("料酒", "2 汤匙"),
            Ingredient("冰糖", "30g"), Ingredient("生姜", "3 片")
        ),
        steps = listOf(
            "排骨剁成小块，冷水下锅焯水，撇去浮沫后捞出备用。",
            "热锅放油，下冰糖小火炒至融化呈琥珀色。",
            "放入排骨翻炒，使其均匀裹上糖色。",
            "加入生抽、老抽、料酒和生姜，翻炒均匀。",
            "加入适量热水，大火烧开后转小火焖煮 30 分钟。",
            "大火收汁，至汤汁浓稠裹在排骨上即可出锅。"
        )
    ),
    Recipe(
        id = "3",
        title = "酸辣汤",
        imageUrl = "https://images.unsplash.com/photo-1547592166-23ac45744acd?w=400",
        lastCookedInfo = "1周前烹饪过",
        category = "汤品",
        cookTime = "30分钟",
        difficulty = "中等",
        cookCount = 8,
        daysSinceLastCook = 7,
        ingredients = listOf(
            Ingredient("豆腐", "1 块"), Ingredient("木耳", "适量"),
            Ingredient("鸡蛋", "1 个"), Ingredient("淀粉", "2 汤匙"),
            Ingredient("醋", "3 汤匙"), Ingredient("辣椒油", "1 汤匙")
        ),
        steps = listOf(
            "豆腐切细丝，木耳泡发后切丝，备用。",
            "锅中加水烧开，放入豆腐丝和木耳丝。",
            "加入生抽、盐调味，大火烧开。",
            "用淀粉加水调成水淀粉，缓慢倒入汤中搅拌勾芡。",
            "打入鸡蛋液，边倒边搅拌形成蛋花。",
            "关火后加入醋和辣椒油，撒上葱花即可。"
        )
    ),
    Recipe(
        id = "4",
        title = "浆果蛋糕",
        imageUrl = "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400",
        lastCookedInfo = "2天前烹饪过",
        category = "蔬菜",
        cookTime = "1小时 30分钟",
        difficulty = "困难",
        cookCount = 3,
        daysSinceLastCook = 2,
        ingredients = listOf(
            Ingredient("低筋面粉", "150g"), Ingredient("鸡蛋", "3 个"),
            Ingredient("细砂糖", "100g"), Ingredient("黄油", "80g"),
            Ingredient("混合浆果", "200g"), Ingredient("淡奶油", "200ml")
        ),
        steps = listOf(
            "烤箱预热至 170°C，模具内铺好烘焙纸。",
            "黄油软化后与白糖打发至颜色发白、体积膨大。",
            "分次加入鸡蛋，每次都充分搅拌均匀。",
            "筛入低筋面粉，用刮刀翻拌至无干粉状态。",
            "面糊倒入模具，送入烤箱烤 40 分钟至竹签插入无粘连。",
            "放凉后用打发奶油抹面，随意摆上新鲜浆果装饰即可。"
        )
    )
)
