package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import com.wang.gates.quickmaths.R
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT


class KeyboardView  : GridLayout{

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {

        val rows = 4
        val cols = 3

        rowCount = rows
        columnCount = cols

        addTopRow()

        for(i in 1..9){
            val button = ButtonView(context, null)
            button.id = i
            button.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                val size = resources.getDimensionPixelSize(R.dimen.keyboard_size)
                height = size
                width = size
            }
            button.text = i.toString()
            addView(button)
        }

    }

    private fun addTopRow(){
        val size = resources.getDimensionPixelSize(R.dimen.keyboard_size)
        val emptySquare1 = EmptySpace(size, size, context, null)
        val button = ButtonView(context, null)
        button.text = "0"
        button.id = 0
        button.layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            height = size
            width = size
        }
        val emptySquare2 = EmptySpace(size, size, context, null)
        addView(emptySquare1)
        addView(button)
        addView(emptySquare2)
    }
}