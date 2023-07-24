package com.sevban.contextandlifecycle.presentation.first_activity.components.custom_button

import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.BOTTOM
import androidx.constraintlayout.widget.ConstraintSet.END
import androidx.constraintlayout.widget.ConstraintSet.PARENT_ID
import androidx.constraintlayout.widget.ConstraintSet.START
import androidx.constraintlayout.widget.ConstraintSet.TOP


internal fun <T : ConstraintSet> BasketLayoutView.setBtnIncreaseQuantityLocation(constraint: T) {
    constraint.apply {
        with(btnIncreaseQuantity.id) {
            connect(this, START, PARENT_ID, START)
            connect(this, BOTTOM, PARENT_ID, BOTTOM)
            connect(this, TOP, PARENT_ID, TOP)
            connect(this, END, tvQuantity.id, START)
        }
    }
}

internal fun <T : ConstraintSet> BasketLayoutView.setTvQuantityLocation(constraint: T) {
    constraint.apply {
        with(tvQuantity.id) {
            connect(this, START, btnIncreaseQuantity.id, END)
            connect(this, TOP, PARENT_ID, TOP)
            connect(this, BOTTOM, PARENT_ID, BOTTOM)
            connect(this, END, PARENT_ID, END)
        }
    }
}