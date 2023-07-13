package com.sevban.contextandlifecycle.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.sevban.contextandlifecycle.R
import com.sevban.contextandlifecycle.ui.theme.dimesions
import com.sevban.contextandlifecycle.ui.theme.textSize
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun LazyColumnWithAnimation(
    timerState: Int,
    onClickShowDialogButton: () -> Unit,
    onClickIncrementCountButton: () -> Unit
) {
    val animatable = remember { Animatable(0f) }
    LaunchedEffect(key1 = true) {
        animatable.animateTo(1f, animationSpec = tween(durationMillis = 5_000))
    }

    LazyColumn(//itemplacementanim & itemspecific anim for the first & + swipe refresh

        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.dimesions.genericComponentSpacing,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            RoundedLeadingIconButton(
                onClick = onClickShowDialogButton,
                textResId = R.string.show_dialog,
                contentDescription = R.string.show_dialogbutton_cd,
                icon = Icons.Rounded.Warning
            )
        }

        item {
            //Text gösterilmiyorken de side effect oluyor.
            //Yani text bu state'i okumasın ama yine de sayımız artıyor
            //ancak recomposition sayısı güncellenmiyor bu sırada.
            Text("Current time: ${timerState}")
            RoundedLeadingIconButton(
                onClick = onClickIncrementCountButton,
                textResId = R.string.increment_counter,
                contentDescription = R.string.increment_counter_button_cd,
                icon = Icons.Rounded.Add
            )
        }
        //
        itemsIndexed(textList) { index, item ->

            AnimatedVisibility(
                visible = if (index != 0)
                    (animatable.value * textList.size).roundToInt() >= index
                else
                    animatable.value > .05f,
                enter = if (index != 0)
                    slideIn(initialOffset = { IntOffset(100, 0) }) + fadeIn() + expandIn { IntSize.Zero }
                else
                    slideIn(initialOffset = { IntOffset(2000, 1000) }) + fadeIn() + expandIn { IntSize.Zero }
            ) {
                Text(
                    text = "item $index",
                    fontSize = MaterialTheme.textSize.genericFontSize,
                )//font-family roboto as default & 14sp default fontsize global
            }
        }
        //accessiblity appliance
    }
}

val textList = listOf("", "", "", "", "", "", "")