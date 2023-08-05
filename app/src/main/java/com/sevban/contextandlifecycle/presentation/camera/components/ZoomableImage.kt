package com.sevban.contextandlifecycle.presentation.camera.components

import android.net.Uri
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun ZoomableImage(
    uri: Uri?,
) {

    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableStateOf(1f) }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = null,
            modifier = Modifier
                .clipToBounds()
                .border(BorderStroke(width = 4.dp, Color.Blue), RectangleShape)
                .pointerInput(Unit) {
                    detectTransformGestures(
                        panZoomLock = false,
                        onGesture = { gestureCentroid: Offset,
                                      gesturePan: Offset,
                                      gestureZoom: Float ->

                            val oldScale = zoom
                            val newScale = (zoom * gestureZoom).coerceIn(1f..3f)
                            offset =
                                (offset + gestureCentroid / oldScale) - (gestureCentroid / newScale) - (gesturePan / oldScale)

                            zoom = newScale

                        }
                    )
                }
                .graphicsLayer {
                    translationX =
                        (-offset.x * zoom).coerceIn(-size.width * (zoom - 1), 0f)
                    translationY =
                        (-offset.y * zoom).coerceIn(-size.height * (zoom - 1), 0f)
                    transformOrigin = TransformOrigin(0f, 0f)

                    scaleX = zoom
                    scaleY = zoom
                },
        )
    }
}