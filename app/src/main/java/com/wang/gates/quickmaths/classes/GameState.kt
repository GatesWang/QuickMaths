package com.wang.gates.quickmaths.classes

import android.os.CountDownTimer
import android.widget.ProgressBar
import com.wang.gates.quickmaths.activities.Game
import kotlinx.android.synthetic.main.game.*
import java.util.*


//this class keeps track of the game state
//time left
//progress of the players

class GameState(var game : Game){
    var settings = GameSettings.getInstance()
    var timeInMilli =  0
    var minTimeInMilli = 0 //this is only used for bomb mode
    var progressBar : ProgressBar? = null

    init{
        //set time
        if(settings.getMode() == GameSettings.TIMER_MODE) {
            when(settings.getDifficulty()){
                GameSettings.EASY -> {
                    timeInMilli = 60000
                }
                GameSettings.MEDIUM -> {
                    timeInMilli = 120000
                }
                GameSettings.HARD -> {
                    timeInMilli = 180000
                }
            }
        }
        else if(settings.getMode() == GameSettings.BOMB_MODE){
            when(settings.getDifficulty()){
                GameSettings.EASY -> {
                    timeInMilli = 10000
                    minTimeInMilli = 3000
                }
                GameSettings.MEDIUM -> {
                    timeInMilli = 15000
                    minTimeInMilli = 8000
                }
                GameSettings.HARD -> {
                    timeInMilli = 25000
                    minTimeInMilli = 15000
                }
            }
        }

        //configure the progress bar
        progressBar = game.progressBar
        progressBar!!.max = timeInMilli/1000
    }

    private var timer : CountDownTimer? = null

    var score : Int = 0

    fun createTimer(){
        progressBar!!.progress = 0
        progressBar!!.max = timeInMilli/1000
        timer = object : CountDownTimer(timeInMilli.toLong(), 1000) {
            var d : Date = Calendar.getInstance().time
            override fun onTick(millis: Long) {
                progressBar!!.incrementProgressBy(1)
            }

            override fun onFinish() {
                game.ifBombModeFinishGame()
            }
        }
    }

    fun startTimer(){
        timer!!.start()
    }

    fun stopTimer(){
        timer!!.cancel()
    }

    fun recalculateTime(){
        if(settings.getMode()==GameSettings.BOMB_MODE){
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