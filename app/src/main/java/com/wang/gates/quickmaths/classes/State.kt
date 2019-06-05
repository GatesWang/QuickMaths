package com.wang.gates.quickmaths.classes

import android.os.CountDownTimer
import android.widget.ProgressBar
import com.wang.gates.quickmaths.activities.Game
import kotlinx.android.synthetic.main.game.*
import java.util.*


//this class keeps track of the game state
//time left
//progress of the players

class State(var game : Game){
    var settings = Settings.getInstance()
    var timeInMilli =  0
    var minTimeInMilli = 0 //this is only used for bomb mode
    var progressBar : ProgressBar? = null
    var currentTime = 0.toLong() //used for pausing

    init{
        //set time
        if(settings.getMode() == Settings.Companion.Mode.TIMER_MODE) {
            when(settings.getDifficulty()){
                Settings.Companion.Difficulty.EASY -> {
                    timeInMilli = 60000
                }
                Settings.Companion.Difficulty.MEDIUM -> {
                    timeInMilli = 120000
                }
                Settings.Companion.Difficulty.HARD -> {
                    timeInMilli = 180000
                }
            }
        }
        else if(settings.getMode() == Settings.Companion.Mode.BOMB_MODE){
            when(settings.getDifficulty()){
                Settings.Companion.Difficulty.EASY -> {
                    timeInMilli = 10000
                    minTimeInMilli = 3000
                }
                Settings.Companion.Difficulty.MEDIUM -> {
                    timeInMilli = 15000
                    minTimeInMilli = 8000
                }
                Settings.Companion.Difficulty.HARD -> {
                    timeInMilli = 25000
                    minTimeInMilli = 15000
                }
            }
        }
        progressBar = game.progress_bar
        resetProgressBar()
    }

    private fun resetProgressBar() {
        progressBar!!.progress = 0
        progressBar!!.max = timeInMilli/1000
    }

    private var timer : CountDownTimer? = null

    var score : Int = 0

    fun createTimer(){
        timer = object : CountDownTimer(timeInMilli.toLong(), 1000) {
            var d : Date = Calendar.getInstance().time
            override fun onTick(millis: Long) {
                currentTime = millis
                progressBar!!.incrementProgressBy(1)
            }

            override fun onFinish() {
                game.finishGame()
            }
        }
        resetProgressBar()
    }

    fun startTimer(){
        timer!!.start()
    }

    fun stopTimer(){
        timer!!.cancel()
    }

    fun recalculateTime(){
        if(settings.getMode()== Settings.Companion.Mode.BOMB_MODE){
            if(timeInMilli>minTimeInMilli){//go down by one second until we hit minimum time
                timeInMilli-=1000
            }

        }
    }

    fun addToCount(){
        score++
        game.problem_count.text = "solved: $score"
    }

}