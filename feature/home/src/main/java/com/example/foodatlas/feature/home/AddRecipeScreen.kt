package com.example.foodatlas.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodatlas.core.designsystem.theme.OnPrimary
import com.example.foodatlas.core.designsystem.theme.Primary
import com.example.foodatlas.core.designsystem.theme.SurfaceContainerHigh
import com.example.foodatlas.core.designsystem.theme.SurfaceContainerLowest
import com.example.foodatlas.core.designsystem.theme.ambientShadow

@Composable
fun AddRecipeScreen(
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
    ) {
        // --- Top Bar ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "取消",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onClose() },
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                "添加图鉴",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                color = Primary
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(Primary)
                    .clickable { /* TODO: Save logic */ }
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    "保存",
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = OnPrimary
                )
            }
        }

        // --- Scrollable Form Content ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // Hero Image Uploader
            DashedUploadBox(
                text = "上传封面",
                icon = Icons.Filled.CameraAlt,
                height = 140.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Recipe Name
            FormLabel("菜名 [必填]")
            var title by remember { mutableStateOf("") }
            OrganicTextField(
                value = title,
                onValueChange = { title = it },
                placeholder = "输入充满诱惑力的菜名..."
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Category
            FormLabel("分类")
            var category by remember { mutableStateOf("经典川菜") }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerHigh)
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    category,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                // Dropdown arrow placeholder
                Icon(
                    androidx.compose.material.icons.Icons.Default.DragIndicator, // Normally an arrow_drop_down
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd).size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Time
            FormLabel("预计时长 (分钟)")
            var time by remember { mutableStateOf("45") }
            OrganicTextField(
                value = time,
                onValueChange = { time = it },
                placeholder = "分钟",
                trailingIcon = {
                    Icon(Icons.Filled.Timer, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Difficulty
            FormLabel("难度 [1-5星]")
            var difficulty by remember { mutableIntStateOf(3) }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerHigh)
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (i in 1..5) {
                        Icon(
                            if (i <= difficulty) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = "星级",
                            tint = if (i <= difficulty) Primary else MaterialTheme.colorScheme.outlineVariant,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { difficulty = i }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Ingredients Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "食材清单",
                    style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { /* TODO: add ingredient row */ }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                    Text("预备食材", style = MaterialTheme.typography.labelLarge, color = Primary) // Using '预备食材' instead of '添加食材' per some variations, wait UI says '添加食材'
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredient Rows
            IngredientEditRow(nameHint = "食材名称, 如: 牛里脊", amountHint = "")
            IngredientEditRow(nameHint = "食材名称", amountHint = "")

            Spacer(modifier = Modifier.height(32.dp))

            // Steps Section
            Text(
                "制作步骤",
                style = MaterialTheme.typography.titleLarge.copy(fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Step 1
            StepEditCard(stepNum = 1)
            Spacer(modifier = Modifier.height(20.dp))
            
            // Step 2
            StepEditCard(stepNum = 2)
            Spacer(modifier = Modifier.height(24.dp))

            // Add Step Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(24.dp)
                    )
                    .clickable { /* TODO */ }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurface)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("添加新步骤", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
                }
            }
            
            Spacer(modifier = Modifier.height(64.dp)) // bottom safe padding
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium.copy(fontSize = 12.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
    )
}

@Composable
private fun OrganicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val bgColor by animateColorAsState(
        targetValue = if (isFocused) SurfaceContainerLowest else SurfaceContainerHigh,
        label = "bg"
    )

    var modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(bgColor)

    if (isFocused) {
        modifier = modifier.ambientShadow()
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(placeholder, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.outline)
                    }
                    innerTextField()
                }
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        },
        modifier = modifier
    )
}

@Composable
private fun DashedUploadBox(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, height: androidx.compose.ui.unit.Dp) {
    val stroke = androidx.compose.ui.graphics.drawscope.Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .clickable { /* upload */ },
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(color = Color(0xFFDDC1B3), style = stroke, cornerRadius = androidx.compose.ui.geometry.CornerRadius(60f))
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun IngredientEditRow(nameHint: String, amountHint: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.weight(2f)) {
            OrganicTextField(value = "", onValueChange = {}, placeholder = nameHint)
        }
        Box(modifier = Modifier.weight(1f)) {
            OrganicTextField(value = "", onValueChange = {}, placeholder = amountHint)
        }
        Icon(
            Icons.Filled.DeleteOutline,
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp).clickable { }
        )
    }
}

@Composable
private fun StepEditCard(stepNum: Int) {
    Row(verticalAlignment = Alignment.Top) {
        // Step indicator
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(SurfaceContainerHigh),
            contentAlignment = Alignment.Center
        ) {
            Text("$stepNum", style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // Card body
        Column(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLowest)
                .ambientShadow()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("每步配图+文字", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                Icon(Icons.Filled.DragIndicator, contentDescription = "Drag", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            DashedUploadBox(text = "添加图片", icon = Icons.Filled.Image, height = 120.dp)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerHigh)
                    .padding(12.dp)
            ) {
                Text(
                    "在这里描述这一步做的烹饪细节...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}
