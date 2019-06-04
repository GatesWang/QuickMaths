package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import com.wang.gates.quickmaths.R


class ButtonView(context: Context, attrs: AttributeSet?) : Button(context, attrs)  {

    init{
        val padding = resources.getDimension(R.dimen.padding).toInt()
        setPadding(padding,padding,padding,padding)
        setBackgroundResource(R.drawable.rounded_rectangle)
        textSize = resources.getDimension(R.dimen.button_text_size)
        setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        typeface = ResourcesCompat.getFont(context, R.font.oswald)
    }
}