package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.wang.gates.quickmaths.R

class EmptySpace(width: Int,
                 height : Int,
                 context: Context,
                 attrs: AttributeSet?) : FrameLayout(context, attrs) {

    init{
        init(width, height, context, attrs)
    }

    private fun init(width : Int , height : Int, context: Context, attrs: AttributeSet?) {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            this.height = height
            this.width = width
        }
    }
}