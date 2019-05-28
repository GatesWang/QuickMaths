package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import android.support.v4.content.ContextCompat
import android.graphics.Typeface
import android.util.TypedValue
import com.wang.gates.quickmaths.R


class Title : TextView{

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context : Context, attrs: AttributeSet) {
        setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        val typeface = Typeface.createFromAsset(context.assets, "fonts/Quango.otf")
        setTypeface(typeface)
        val fontSize = resources.getDimension(R.dimen.title_font_size)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
    }

}