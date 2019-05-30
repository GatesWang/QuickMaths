package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wang.gates.quickmaths.classes.Problem

class DrawView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var mPaint = Paint()
    private var mPath = Path()
    private var problem : Problem? = null

    private var mCurX = 0f
    private var mCurY = 0f
    private var mStartX = 0f
    private var mStartY = 0f

    init {
        mPaint.apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            strokeWidth = 8f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        setTextSize(300)
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

    fun clearPath() {
        mPath.reset()
        invalidate()
    }

    fun setProblem(problem : Problem){
        this.problem = problem
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(mPath, mPaint)
        if(problem != null){
            drawProblem(canvas)
        }
    }

    private fun drawProblem(canvas: Canvas){
        val problemParts = problem!!.getProblemParts()
        if(problemParts[1].equals("/")){
            var startXPart = (0 + mPaint.textSize*.6).toFloat()
            var startYPart = (0 + mPaint.textSize*2.5).toFloat()
            val spacing = 5

            canvas.drawText(problemParts[2], startXPart, startYPart, mPaint)
            startXPart += mPaint.textSize
            drawDivisionOperator(canvas, startXPart, startYPart)
            startXPart += mPaint.textSize + spacing
            canvas.drawText(problemParts[0], startXPart, startYPart, mPaint)
        }
        else{
            var startXPart = (0 + mPaint.textSize*1.5).toFloat()
            var startYPart = (0 + mPaint.textSize*1.8).toFloat()
            val spacing = 10
            for(part in problemParts){
                canvas.drawText(part, startXPart, startYPart, mPaint)
                startYPart += mPaint.textSize + spacing
            }
            drawHorizontalLine(canvas, startYPart)
        }
    }

    private fun drawHorizontalLine(canvas: Canvas, y : Float){
        val startXLine = (mPaint.textSize*.5).toFloat()
        val endXLine = (mPaint.textSize*3)

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mStartX = x
                mStartY = y
                actionDown(x, y)
            }
            MotionEvent.ACTION_MOVE -> actionMove(x, y)
            MotionEvent.ACTION_UP -> actionUp()
        }

        invalidate()
        return true
    }

    private fun actionDown(x: Float, y: Float) {
        mPath.moveTo(x, y)
        mCurX = x
        mCurY = y
    }

    private fun actionMove(x: Float, y: Float) {
        mPath.quadTo(mCurX, mCurY, (x + mCurX) / 2, (y + mCurY) / 2)
        mCurX = x
        mCurY = y
    }

    private fun actionUp() {
        mPath.lineTo(mCurX, mCurY)

        // draw a dot on click
        if (mStartX == mCurX && mStartY == mCurY) {
            mPath.lineTo(mCurX, mCurY + 2)
            mPath.lineTo(mCurX + 1, mCurY + 2)
            mPath.lineTo(mCurX + 1, mCurY)
        }
    }


}