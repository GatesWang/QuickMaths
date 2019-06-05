package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import com.wang.gates.quickmaths.classes.Problem

class DrawProblemView(context: Context, attrs: AttributeSet) : DrawView(context, attrs){
    private var problem : Problem? = null

    init{
        setTextSize(250)
    }

    private fun setTextSize(desiredWidth : Int){
        val testTextSize = 48f
        // Get the bounds of the text, using our testTextSize.
        mPaint.setTextSize(testTextSize)
        val bounds = Rect()
        mPaint.getTextBounds(problem.toString(), 0, problem.toString().length, bounds)
        // Calculate the desired size as a proportion of our testTextSize.
        val desiredTextSize = testTextSize * desiredWidth / bounds.width()
        // Set the paint for that size.
        mPaint.setTextSize(desiredTextSize)
    }

    fun setProblem(problem : Problem){
        this.problem = problem
    }

    private fun drawProblem(canvas: Canvas){
        val (operand1, operator , operand2) = problem!!.getProblemParts()
        if(operator.equals("/")){
            var startXPart = (0 + mPaint.textSize*.6).toFloat()
            var startYPart = (0 + mPaint.textSize*2.5).toFloat()
            val spacing = 5

            canvas.drawText(operand2, startXPart, startYPart, mPaint)
            startXPart += mPaint.textSize
            drawDivisionOperator(canvas, startXPart, startYPart)
            startXPart += mPaint.textSize + spacing
            canvas.drawText(operand1, startXPart, startYPart, mPaint)
        }
        else{
            mPaint.textAlign = Paint.Align.RIGHT
            var startXPart = (0 + mPaint.textSize*3.1).toFloat()
            var startYPart = (0 + mPaint.textSize*1.8).toFloat()
            val spacing = 10

            canvas.drawText(operand1, startXPart, startYPart, mPaint)
            startYPart += mPaint.textSize + spacing
            canvas.drawText("$operator $operand2", startXPart, startYPart, mPaint)
            startYPart += mPaint.textSize + spacing
            drawHorizontalLine(canvas, startYPart)
            mPaint.textAlign = Paint.Align.CENTER
        }
    }



    private fun drawHorizontalLine(canvas: Canvas, y : Float){
        val startXLine = (mPaint.textSize*.5).toFloat()
        val endXLine = (mPaint.textSize*3.5).toFloat()

        val startYLine = (y - mPaint.textSize*.5).toFloat()
        val endYLine = (y - mPaint.textSize*.5).toFloat()

        canvas.drawLine(
            startXLine,
            startYLine,
            endXLine,
            endYLine,
            mPaint)
    }

    private fun drawDivisionOperator(canvas: Canvas, x : Float, y : Float){
        //vertical
        var startXLine = x - 35
        var endXLine = x - 35

        var startYLine = (y - mPaint.textSize)
        var endYLine = y

        canvas.drawLine(
            startXLine,
            startYLine,
            endXLine,
            endYLine,
            mPaint)

        //horizontal
        endXLine = (x + mPaint.textSize*(2.5).toFloat())
        endYLine = (y - mPaint.textSize)

        canvas.drawLine(
            startXLine,
            startYLine,
            endXLine,
            endYLine,
            mPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if(problem != null){
            drawProblem(canvas)
        }
    }
}