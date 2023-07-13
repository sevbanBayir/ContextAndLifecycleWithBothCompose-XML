package com.sevban.contextandlifecycle.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sevban.contextandlifecycle.ui.theme.dimesions
import com.sevban.contextandlifecycle.ui.theme.textSize

@Composable
fun RoundedBlackAndWhiteButton(
    onClick: () -> Unit,
    @StringRes
    textResId: Int,
    @StringRes
    contentDescription: Int,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground,
        ),
        shape = RoundedCornerShape(MaterialTheme.dimesions.mediumCornerRadius)
    ) {

        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            modifier = Modifier.padding(MaterialTheme.dimesions.leadingIconSpacing),
            contentDescription = stringResource(id = contentDescription)
        )
        Text(
            text = stringResource(id = textResId),
            fontSize = MaterialTheme.textSize.genericFontSize
        )

    }
}