package com.sevban.contextandlifecycle.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.sevban.contextandlifecycle.R

class MidRoundedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {

    private val backgroundPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.black)
        isAntiAlias = true
    }

    private val cornerRadius = resources.getDimension(R.dimen.rounded_corner_radius)

    override fun onDraw(canvas: Canvas) {
        val path = Path()
        val rect = canvas.clipBounds

        path.addRoundRect(
            rect.left.toFloat(),
            rect.top.toFloat(),
            rect.right.toFloat(),
            rect.bottom.toFloat(),
            cornerRadius,
            cornerRadius,
            Path.Direction.CW
        )

        canvas.drawPath(path, backgroundPaint)

        super.onDraw(canvas)
    }
}
