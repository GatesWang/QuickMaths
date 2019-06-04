package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.wang.gates.quickmaths.R
import android.text.Selection.getSelectionEnd
import android.text.Selection.getSelectionStart





class SubmitAnswerView : ConstraintLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val v = LayoutInflater.from(context).inflate(R.layout.submit_answer, this, true)

        //disable keyboard
        val editText = v.findViewById<EditText>(R.id.input)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // API 21
            editText.setShowSoftInputOnFocus(false)
        } else { // API 11-20
            editText.setTextIsSelectable(true)
        }

        //set button clicks
        for(i in 0..9){
            val button = v.findViewById<KeyboardView>(R.id.keyboard).findViewById<Button>(i)
            button.setOnClickListener{
                val start = Math.max(editText.getSelectionStart(), 0)
                val end = Math.max(editText.getSelectionEnd(), 0)
                editText.getText().replace(
                    Math.min(start, end), Math.max(start, end),
                    i.toString(), 0, 1
                )
            }
        }

        //backspace
        val backspace = v.findViewById<ButtonView>(R.id.backspace)
        backspace.setOnClickListener{
            val stringText = editText.text.toString()
            if(stringText.isNotEmpty()){
                val start = Math.max(editText.getSelectionStart(), 0)
                val end = Math.max(editText.getSelectionEnd(), 0)
                if(start==end){
                    editText.getText().replace(
                        Math.min(start, end)-1, Math.max(start, end),
                        "", 0, 0
                    )
                }
                else{
                    editText.getText().replace(
                        Math.min(start, end), Math.max(start, end),
                        "", 0, 0
                    )
                }
            }
        }
        backspace.setOnLongClickListener{
            editText.setText("")
            true
        }

    }

}