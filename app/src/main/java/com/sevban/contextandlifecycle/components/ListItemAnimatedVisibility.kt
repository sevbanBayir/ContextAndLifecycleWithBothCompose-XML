package com.sevban.contextandlifecycle.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.jvm.internal.Ref.FloatRef
import kotlin.math.roundToInt

@Composable
fun AnimatedItem(
    index: Int,
    itemCount: Int,
    progress : Float,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = if (index != 0)
            (progress * itemCount).roundToInt() >= index
        else
            progress > .000001f,
        enter = if (index != 0)
            slideIn(initialOffset = { IntOffset(100, 0) }) + fadeIn() + expandIn { IntSize.Zero }
        else
            slideIn(initialOffset = { IntOffset(200, 100) }) + fadeIn() + expandIn { IntSize.Zero } ,
        content = content
    )
}