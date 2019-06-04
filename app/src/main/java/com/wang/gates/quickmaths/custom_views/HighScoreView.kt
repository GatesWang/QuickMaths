package com.wang.gates.quickmaths.custom_views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.GridLayout
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.Settings


class HighScoreView  : GridLayout{
    private val modes = Settings.Companion.Mode.values()
    private val difficulties = Settings.Companion.Difficulty.values()

    private val numModes = modes.size
    private val numDifficulties = difficulties.size

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet) {
        rowCount = numModes * 4
        columnCount = 3
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).also{

        }
        populateGrid()
    }

    private fun populateGrid(){
        for(modeIndex in 0..numModes-1){
            addHeader(modeIndex)
            addHighScores(modeIndex)
        }
    }

    private fun addHeader(modeIndex : Int){
        val title = TitleView(context,null)
        title.setText(modes[modeIndex].string)
        addView(title)
        addView(EmptySpace(0, 0, context, null))
        addView(EmptySpace(0, 0, context, null))
    }

    private fun addHighScores(mode : Int){
        val width = resources.getDimension(R.dimen.high_score_width).toInt()
        val height = resources.getDimension(R.dimen.high_score_height).toInt()

        val mode = modes.get(mode)

        for(difficultyIndex in 0..numDifficulties-1){
            val fontSize = resources.getDimension(R.dimen.title_font_size) - 14
            val difficulty = difficulties[difficultyIndex]

            val difficultyLabel = TitleView(context, null)
            difficultyLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            difficultyLabel.setText(difficulties[difficultyIndex].string)
            addView(difficultyLabel)

            addView(EmptySpace(width, height, context, null))

            val highScoreLabel = TitleView(context, null)
            highScoreLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
            val highScore = Settings.getInstance().getHighScore(context, mode, difficulty)
            highScoreLabel.setText(highScore.toString())
            addView(highScoreLabel)
        }
    }
}