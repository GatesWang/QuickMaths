package com.wang.gates.quickmaths.classes

import android.os.CountDownTimer
import android.util.Log
import com.wang.gates.quickmaths.activities.Game
import kotlinx.android.synthetic.main.game.*
import java.util.*
import java.util.concurrent.TimeUnit


//this class keeps track of the game state
//time left
//progress of the players

class GameState(var game : Game){
    var settings = GameSettings.getInstance()
    var timeInMilli = 0

    private var timer : CountDownTimer? = null

    var count : Int = 0

    private fun calculateTime(){
        //for timer mode we need to convert 0 -> 60000, 1 -> 120000, 2 -> 180000
        //for bomb mode we need to convert 3 -> 5000, 4 -> 10000, 5 -> 15000
        if(settings.getMode()==0){
            timeInMilli = (settings.getTimeControl()+1)*60000
        }
        else if(settings.getMode()==1) {
            timeInMilli = (settings.getTimeControl() - 2) * 5000
        }
        else {

        }
    }

    private fun createTimer(){
        calculateTime()
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
        createTimer()
        timer!!.start()
    }

    fun stopTimer(){
        timer!!.cancel()
    }

    fun addToCount(){
        count++
        game.problem_count.text = "solved: $count"
    }

}