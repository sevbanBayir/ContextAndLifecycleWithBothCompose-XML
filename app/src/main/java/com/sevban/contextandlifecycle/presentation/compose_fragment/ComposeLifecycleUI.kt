package com.sevban.contextandlifecycle.presentation.compose_fragment

import android.util.Log
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sevban.contextandlifecycle.presentation.compose_fragment.components.ComposeDialog
import com.sevban.contextandlifecycle.presentation.compose_fragment.components.DisposableEffectWithLifecycle
import com.sevban.contextandlifecycle.presentation.compose_fragment.components.LazyColumnWithAnimation

private val TAG = "ComposeLifecycleFragment"

@Composable
fun ComposeLifecycleUI(
    navigate: () -> Unit,
) {
    Surface {

        var showDialog by remember { mutableStateOf(false) }
        val timerState = remember { mutableStateOf(0) }

        if (showDialog)
            ComposeDialog(
                onDismiss = { showDialog = !showDialog },
                onClickOKButton = { showDialog = !showDialog }
            )

        timerState.value++
        LazyColumnWithAnimation(
            timerState = timerState.value,
            onClickShowDialogButton = { showDialog = !showDialog },
            onClickIncrementCountButton = { timerState.value++ },
            navigate = navigate
        )

        DisposableEffectWithLifecycle(
            onResume = {
                Log.i(TAG, "onResume in Compose View")
            },
            onCreate = {
                Log.i(TAG, "onCreate in  Compose View")
            },
            onDestroy = {
                Log.i(TAG, "onDestroy in Compose View")
            },
            onPause = {
                Log.i(TAG, "onPause in Compose View")
            },
            onStart = {
                Log.i(TAG, "onStart in Compose View")
            },
            onStop = {
                Log.i(TAG, "onStop in Compose View")
            }
        )
    }
}