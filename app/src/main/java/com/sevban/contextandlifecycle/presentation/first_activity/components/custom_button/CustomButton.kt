package com.sevban.contextandlifecycle.presentation.first_activity.components.custom_button

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.sevban.contextandlifecycle.R


enum class State {
    Loading,
    None
}

class BasketLayoutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private var state: State = State.None
    var listener: BasketLayoutViewListener? = null

    fun setBasketLayoutListener(listener: BasketLayoutViewListener) {
        this.listener = listener
    }

    private var incrementValue = 1
    private var quantity: Int = 1
    private var maxQuantity: Int? = null

    internal val padding = 16.toPx.toInt()
    private var isAddedViews: Boolean = false


    private val clContainer: ConstraintLayout by lazy {
        ConstraintLayout(context).apply {
            id = generateViewId()
        }
    }

    internal val btnIncreaseQuantity: AppCompatImageButton by lazy {
        AppCompatImageButton(context).apply {
            id = generateViewId()
            background = null
            setOnClickListener {
                if (state == State.None) {
                    if (maxQuantity != null && quantity + incrementValue > (maxQuantity ?: 0)) {
                        listener?.onExceedMaxQuantity(quantity)
                    } else {
                        listener?.onClickIncreaseQuantity(quantity + incrementValue)
                    }
                }
            }
            setColorFilter(ContextCompat.getColor(context, R.color.black))
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_add_24))
        }
    }

    internal val tvQuantity: AppCompatTextView by lazy {
        AppCompatTextView(context).apply {
            id = generateViewId()
            text = "$quantity"
            setTextColor(Color.GREEN)
            gravity = Gravity.CENTER
            width = 36.toPx.toInt()
            height = 36.toPx.toInt()
/*            val shape = ShapeAppearanceModel.Builder()
                .setAllCorners(RoundedCornerTreatment()).setAllCornerSizes(RelativeCornerSize(0.5f))
                .build()
            val backgroundDrawable = MaterialShapeDrawable(shape).apply {
                fillColor = ContextCompat.getColorStateList(context, R.color.green_transparent)
            }
            ViewCompat.setBackground(this, backgroundDrawable)*/
        }
    }

    init {
        initializeSubViews()
    }

    private fun initializeSubViews() {
        addViews(isAddedViews)
        connectViews()
        isAddedViews = true
    }


    private fun addViews(isAddedViews: Boolean) {
        if (!isAddedViews) {
            addView(clContainer)
            with(clContainer) {
                addView(btnIncreaseQuantity)
                addView(tvQuantity)
            }
        }
    }

    private fun connectViews() {
        if (!isAddedViews) {
            ConstraintSet().apply {
                clone(clContainer)
                setBtnIncreaseQuantityLocation(this)
                setTvQuantityLocation(this)
                applyTo(clContainer)
            }
        }
    }

    fun setBasketQuantity(quantity: Int, state: State = State.None) {
        this.quantity = quantity
        this.state = state
        btnIncreaseQuantity.isEnabled = true
        tvQuantity.isVisible = true
        setTvQuantity(quantity)
    }


    private fun setTvQuantity(quantity: Int) {
        tvQuantity.text = "$quantity"
    }
}

interface BasketLayoutViewListener {
    fun onClickIncreaseQuantity(quantity: Int)
    fun onExceedMaxQuantity(quantity: Int)
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )