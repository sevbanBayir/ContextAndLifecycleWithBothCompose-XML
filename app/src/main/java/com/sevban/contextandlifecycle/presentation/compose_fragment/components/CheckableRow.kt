package com.sevban.contextandlifecycle.presentation.compose_fragment.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun CheckableRow() {
    MaterialTheme {
        var checked by remember { mutableStateOf(false) }
        Row(
            Modifier
                .toggleable(
                    value = checked,
                    role = Role.Checkbox,
                    onValueChange = { checked = !checked }
                )
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Option", Modifier.weight(1f))
            Checkbox(checked = checked, onCheckedChange = null)
        }
    }
}