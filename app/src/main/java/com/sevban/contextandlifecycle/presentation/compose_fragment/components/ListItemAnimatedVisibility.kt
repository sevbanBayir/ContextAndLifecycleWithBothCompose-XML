package com.sevban.contextandlifecycle.presentation.compose_fragment.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

const val ARBITRARY_MIN_THRESHOLD_FOR_ITEM_ANIM = 0.00001F

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedItem(
    index: Int,
    itemCount: Int,
    progress: Float,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = if (index != 0)
            (progress * itemCount).roundToInt() >= index
        else
            progress > ARBITRARY_MIN_THRESHOLD_FOR_ITEM_ANIM,
        enter = if (index != 0)
            slideIn(initialOffset = { IntOffset(100, 0) }) + fadeIn() + expandIn { IntSize.Zero }
        else
            scaleIn(
                animationSpec = spring(
                    dampingRatio = 15f,
                    stiffness = Spring.StiffnessHigh
                )
            ) + fadeIn() + expandIn { IntSize.Zero },
        content = content
    )
}