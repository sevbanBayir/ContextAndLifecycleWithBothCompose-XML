package com.sevban.contextandlifecycle.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimensions(
    val defaultValue: Dp = 0.dp,
    val genericComponentSpacing: Dp = 32.dp,
    val mediumCornerRadius: Dp = 8.dp,
    val leadingIconSpacing : Dp = 4.dp,
    val cardElevation: Dp = 8.dp
)

data class TextSize (
    val defaultValue : TextUnit = 14.sp,
    val genericFontSize: TextUnit = 14.sp
)

val LocaDimension = compositionLocalOf { Dimensions() }
val LocalTextSize = compositionLocalOf { TextSize() }

val MaterialTheme.dimesions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocaDimension.current

val MaterialTheme.textSize: TextSize
    @Composable
    @ReadOnlyComposable
    get() = LocalTextSize.current