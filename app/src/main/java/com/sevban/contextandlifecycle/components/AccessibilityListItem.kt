package com.sevban.contextandlifecycle.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import com.sevban.contextandlifecycle.R
import com.sevban.contextandlifecycle.ui.theme.textSize

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccessibilityListItem(
    text: String,
    onClickFavButton: () -> Unit,
    isFavourited: Boolean,
) {

    val actionLabel = stringResource(
        if (isFavourited) R.string.unfavourite else R.string.favourite
    )

    val action = fun() : Boolean {
        println("anon")
        return true
    }
    Column(
        modifier = Modifier
            .clickable(
                onClick = {
                    println("row's action")
                },
                onClickLabel = stringResource(id = R.string.activate_list_action)
            )
            .semantics {
                customActions = listOf(
                    CustomAccessibilityAction(label = actionLabel, action = action)
                )
            },
    ) {
        ListItem(

            text = {
                Text(
                    text = "Overline ListItem",
                    fontSize = MaterialTheme.textSize.genericFontSize
                )
            },
            overlineText = { Text(text) },
            icon = {
                IconButton(
                    onClick = onClickFavButton,
                    modifier = Modifier.clearAndSetSemantics { }
                ) {
                    val tint by animateColorAsState(
                        targetValue = if (isFavourited) Color(0xffE91E63) else Color(0xffB0BEC5),
                        animationSpec = tween(durationMillis = 400),
                        label = "Fav button animation"
                    )

                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = stringResource(id = R.string.favourite_button),
                        tint = tint,
                    )
                }
            },
        )
        Divider()
    }
}