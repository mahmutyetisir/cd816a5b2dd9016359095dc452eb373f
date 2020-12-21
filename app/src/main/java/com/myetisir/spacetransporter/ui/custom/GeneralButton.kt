package com.myetisir.spacetransporter.ui.custom

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import com.google.android.material.button.MaterialButton
import com.myetisir.spacetransporter.R
import com.myetisir.spacetransporter.common.px

class GeneralButton constructor(
    context: Context,
    attrs: AttributeSet? = null
) : MaterialButton(context, attrs) {


    init {
        gravity = Gravity.CENTER

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextColor(context.getColor(R.color.black))
            setBackgroundColor(context.getColor(R.color.white))
            backgroundTintList = ColorStateList.valueOf(context.getColor(R.color.white))
            strokeColor = ColorStateList.valueOf(context.getColor(R.color.black))
            rippleColor = ColorStateList.valueOf(context.getColor(R.color.blue_500))
        }

        isAllCaps = false
        textAlignment = View.TEXT_ALIGNMENT_CENTER
        setTypeface(typeface, Typeface.BOLD)
        elevation = 0f
        strokeWidth = 4
        stateListAnimator = null

        minHeight = 45.px
    }
}