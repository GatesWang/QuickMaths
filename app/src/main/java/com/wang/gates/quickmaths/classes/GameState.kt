package com.wang.gates.quickmaths.classes

import android.os.CountDownTimer
import android.util.Log
import com.wang.gates.quickmaths.activities.Game
import com.wang.gates.quickmaths.activities.Settings
import kotlinx.android.synthetic.main.game.*
import java.util.*
import java.util.concurrent.TimeUnit


//this class keeps track of the game state
//time left
//progress of the players

class GameState(var game : Game){
    var settings = GameSettings.getInstance()
    var timeInMilli =  0
    var minTimeInMilli = 0 //this is only used for bomb mode

    init{
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
    }


    private var timer : CountDownTimer? = null

    var count : Int = 0

    fun createTimer(){
        timer = object : CountDownTimer(timeInMilli.toLong(), 1000) {
            var d : Date = Calendar.getInstance().time
            override fun onTick(millis: Long) {
                val ms = String.format(
                    "%01d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1)
                )
                game.time_left.text = ms
            }

            override fun onFinish() {
                game.finishGame()
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
        count++
        game.problem_count.text = "solved: $count"
    }

}