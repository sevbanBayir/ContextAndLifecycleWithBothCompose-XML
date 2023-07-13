package com.sevban.contextandlifecycle.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.sevban.contextandlifecycle.R
import com.sevban.contextandlifecycle.ui.theme.dimesions

@Composable
fun ComposeDialog(
    onDismiss: () -> Unit,
    onClickOKButton: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    MaterialTheme.dimesions.genericComponentSpacing,//common
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.sample_compose_dialog_message))//3 lang
                Button(onClick = onClickOKButton) {
                    Text(text = "OK")
                }
            }
        }
    }
}