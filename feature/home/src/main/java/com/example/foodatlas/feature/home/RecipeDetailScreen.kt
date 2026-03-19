package com.example.foodatlas.feature.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.foodatlas.core.designsystem.theme.OnPrimary
import com.example.foodatlas.core.designsystem.theme.OnSecondaryContainer
import com.example.foodatlas.core.designsystem.theme.Primary
import com.example.foodatlas.core.designsystem.theme.SecondaryContainer
import com.example.foodatlas.core.designsystem.theme.SurfaceContainerLow
import com.example.foodatlas.core.designsystem.theme.SurfaceContainerLowest

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit
) {
    // ← 关键：拦截系统的返回手势/按钮（左划返回、实体返回键）
    // 没有这行，系统的返回动作会传递给 Activity，导致直接退出 App
    BackHandler(enabled = true) {
        onBack()
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 48.dp)
        ) {
            // ── Hero Image ───────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = recipe.imageUrl,
                        contentDescription = recipe.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Gradient overlay at top for back button legibility
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(
                                Brush.verticalGradient(
                                    0f to MaterialTheme.colorScheme.scrim.copy(alpha = 0.45f),
                                    1f to MaterialTheme.colorScheme.scrim.copy(alpha = 0f)
                                )
                            )
                    )
                }
            }

            // ── Recipe Info Card ─────────────────────────
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 20.dp)
                ) {
                    // Category chip
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SecondaryContainer)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = recipe.category,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
                            color = OnSecondaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Recipe title
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Meta info row (Time + Difficulty)
                    Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        MetaInfoChip(
                            icon = Icons.Filled.AccessTime,
                            label = "耗时",
                            value = recipe.cookTime
                        )
                        MetaInfoChip(
                            icon = Icons.Filled.LocalDining,
                            label = "难度",
                            value = recipe.difficulty
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ── Stats Cards Row ─────────────────────
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // 累计制作
                        StatCard(
                            label = "累计制作",
                            value = "${recipe.cookCount}",
                            unit = "次",
                            modifier = Modifier.weight(1f)
                        )
                        // 距上次制作
                        val sinceLastLabel = recipe.daysSinceLastCook?.let { "${it}" } ?: "—"
                        val sinceLastUnit = if (recipe.daysSinceLastCook != null) "天" else ""
                        StatCard(
                            label = "距上次制作",
                            value = sinceLastLabel,
                            unit = sinceLastUnit,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // ── Ingredients Section ──────────────────────
            item { SectionHeader(title = "食材清单") }

            itemsIndexed(recipe.ingredients) { index, ingredient ->
                IngredientRow(ingredient = ingredient, isAlternate = index % 2 == 1)
            }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            // ── Steps Section ────────────────────────────
            item {
                SectionHeader(title = "烹饪步骤")
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(recipe.steps) { index, step ->
                StepItem(stepNum = index + 1, total = recipe.steps.size, text = step)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // ── Floating Back Button (always on top) ─────────
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 8.dp, top = 8.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
        ) {
            Icon(
                Icons.Filled.ArrowBackIosNew,
                contentDescription = "返回",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ── Stat Card ─────────────────────────────────────────────────────────────────

@Composable
private fun StatCard(
    label: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLowest)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = Primary   // 橙褐色醒目数字
            )
            if (unit.isNotEmpty()) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
        }
    }
}

// ── Helpers ───────────────────────────────────────────────────────────────────

@Composable
private fun MetaInfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(label,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp,
                    fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun IngredientRow(ingredient: Ingredient, isAlternate: Boolean) {
    val bg = if (isAlternate) SurfaceContainerLow else MaterialTheme.colorScheme.surface
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(ingredient.name,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp),
            color = MaterialTheme.colorScheme.onSurface)
        Text(ingredient.amount,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
            color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
private fun StepItem(stepNum: Int, total: Int, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Primary),
            contentAlignment = Alignment.Center
        ) {
            Text("$stepNum",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = OnPrimary)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text("第 $stepNum 步 / 共 $total 步",
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
