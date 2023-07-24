package com.sevban.contextandlifecycle.presentation.camera.components

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculateCentroidSize
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputScope
import kotlin.math.abs

suspend fun PointerInputScope.detectTransformGestures(
    panZoomLock: Boolean = false,
    onGesture: (centroid: Offset, pan: Offset, zoom: Float) -> Unit,
    onDoubleTap: (Offset) -> Unit
) {
    awaitEachGesture {
        var zoom = 1f
        var pan = Offset.Zero
        var pastTouchSlop = false
        val touchSlop = viewConfiguration.touchSlop
        val down: PointerInputChange = awaitFirstDown()

        var pointerId = down.id

        val doubleTap = awaitSecondDown(down)
        if (doubleTap != null) {
            onDoubleTap(doubleTap.position)
        } else {

            do {
                val event = awaitPointerEvent()

                val canceled = event.changes.any { it.isConsumed }

                if (!canceled) {

                    val zoomChange = event.calculateZoom()
                    val panChange = event.calculatePan()

                    if (!pastTouchSlop) {
                        zoom *= zoomChange
                        pan += panChange

                        val centroidSize = event.calculateCentroidSize(useCurrent = false)
                        val zoomMotion = abs(1 - zoom) * centroidSize
                        val panMotion = pan.getDistance()

                        if (zoomMotion > touchSlop || panMotion > touchSlop) {
                            pastTouchSlop = true
                        }
                    }

                    if (pastTouchSlop) {
                        val centroid = event.calculateCentroid(useCurrent = false)

                        if (zoomChange != 1f || panChange != Offset.Zero) {
                            onGesture(
                                centroid,
                                panChange,
                                zoomChange,
                            )
                        }

                        val cancel = event.changes.find {
                            it.id.value == pointerId.value + 2 ||
                                    it.id.value == pointerId.value + 1
                        }

                        zoom *= event.calculateZoom()
                        if (cancel != null && doubleTap != null)
                            event.changes.forEach {
                                it.consume()
                            }

                    }
                }
            } while (!canceled && event.changes.any { it.pressed })
        }
    }
}

private suspend fun AwaitPointerEventScope.awaitSecondDown(
    firstUp: PointerInputChange
): PointerInputChange? = withTimeoutOrNull(viewConfiguration.doubleTapTimeoutMillis) {
    val minUptime = firstUp.uptimeMillis + viewConfiguration.doubleTapMinTimeMillis
    var change: PointerInputChange
    // The second tap doesn't count if it happens before DoubleTapMinTime of the first tap
    do {
        change = awaitFirstDown()
    } while (change.uptimeMillis < minUptime)
    change
}