package com.sevban.contextandlifecycle.presentation.compose_fragment.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.sevban.contextandlifecycle.ui.theme.dimesions
import com.sevban.contextandlifecycle.ui.theme.textSize

@Composable
fun RoundedLeadingIconButton(
    onClick: () -> Unit,
    @StringRes
    textResId: Int,
    @StringRes
    contentDescription: Int,
    icon: ImageVector,
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
            imageVector = icon,
            modifier = Modifier.padding(MaterialTheme.dimesions.leadingIconSpacing),
            contentDescription = stringResource(id = contentDescription)
        )
        Text(
            text = stringResource(id = textResId),
            fontSize = MaterialTheme.textSize.genericFontSize
        )
    }
}