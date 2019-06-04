package com.wang.gates.quickmaths.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridLayout
import android.widget.TextView
import com.wang.gates.quickmaths.R
import kotlinx.android.synthetic.main.game_high_score.*

class GameHighScore : AppCompatActivity() {

    companion object{
        val modes = arrayOf("timer", "bomb")
        val difficulties = arrayOf("easy", "medium", "hard")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_high_score)
        addTimerModeGrid()
        addBombModeGrid()
    }

    private fun addTimerModeGrid(){
        val grid = GridLayout(this@GameHighScore)
        grid.columnCount = 3
        grid.rowCount = 2

        val labelEasy = TextView(this@GameHighScore)
        labelEasy.text = "Easy"
        grid.addView(labelEasy)

        val labelMedium = TextView(this@GameHighScore)
        labelMedium.text = "Medium"
        grid.addView(labelMedium)

        val labelHard = TextView(this@GameHighScore)
        labelHard.text = "Hard"
        grid.addView(labelHard)


        high_score_layout.addView(grid)
    }

    private fun addBombModeGrid(){

    }
}