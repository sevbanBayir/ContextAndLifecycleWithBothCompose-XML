package com.sevban.contextandlifecycle.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sevban.contextandlifecycle.R
import com.sevban.contextandlifecycle.ui.theme.dimesions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LazyColumnWithAnimation(
    timerState: Int,
    onClickShowDialogButton: () -> Unit,
    onClickIncrementCountButton: () -> Unit,
) {
    var animatable = Animatable(0f)
    var isLoading by remember { mutableStateOf(false) }
    val corouteScope = rememberCoroutineScope()
    LaunchedEffect(key1 = isLoading) {
        delay(2.seconds)
        isLoading = false
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            isLoading = true
        }
    )

    LaunchedEffect(key1 = true, key2 = isLoading) {
        animatable.animateTo(1f, animationSpec = tween(durationMillis = 2_000))
    }
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
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
            if (!isLoading)
                itemsIndexed(textList) { index, item ->
                    var isFavourited by remember { mutableStateOf(false) }

                    AnimatedItem(
                        index = index,
                        itemCount = textList.size,
                        progress = animatable.value
                    ) {
                        OverlinedListItem(
                            text = "item $index",
                            isFavourited = isFavourited,
                            onClickFavButton = { isFavourited = !isFavourited }
                        )//font-family roboto as default & 14sp default fontsize global
                    }
                }

            item {
                CheckableRow()
                //accessiblity appliance
            }
        }
        PullRefreshIndicator(
            refreshing = isLoading,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}

val textList = listOf("", "", "", "", "", "", "", "", "", "", "", "")