package com.sevban.contextandlifecycle.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.sevban.contextandlifecycle.ui.theme.textSize

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverlinedListItem(
    text: String,
    onClickFavButton: (Boolean) -> Unit,
    isFavourited: Boolean,
) {
    val tint by animateColorAsState(
        targetValue = if (isFavourited) Color(0xffE91E63) else Color(0xffB0BEC5),
        animationSpec = tween(durationMillis = 400),
        label = ""
    )
    Column {
        ListItem(
            text = {
                Text(
                    text = "Overline ListItem",
                    fontSize = MaterialTheme.textSize.genericFontSize
                )
            },
            overlineText = { Text(text) },
            icon = {
                IconToggleButton(
                    onCheckedChange = onClickFavButton,
                    checked = isFavourited,
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = "Localized description",
                        tint = tint,
                    )
                }
            },
        )
        Divider()
    }
}