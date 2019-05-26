package com.wang.gates.quickmaths

import android.content.Context
import android.preference.PreferenceManager
import android.widget.TextView

class ScoreTracker(context : Context, scoreTextView : TextView) {
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = preferences.edit()
    private var score : Int = preferences.getInt("score", 0)
    private val scoreTextView = scoreTextView

    init{
        scoreTextView.text = "$score points"
    }

    fun increaseScore(difficulty : Int){
        when(difficulty){
            ProblemGenerator.EASY ->{
                score+=1
            }
            ProblemGenerator.MEDIUM ->{
                score+=2
            }
            ProblemGenerator.HARD ->{
                score+=3
            }
        }
        editor.putInt("score", score)
        scoreTextView.text = "$score points"
        editor.commit()
    }
}