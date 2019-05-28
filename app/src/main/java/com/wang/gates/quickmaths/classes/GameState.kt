package com.wang.gates.quickmaths.classes

import android.content.Intent
import android.os.CountDownTimer
import com.wang.gates.quickmaths.activities.Game
import kotlinx.android.synthetic.main.game.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.datatype.DatatypeConstants.MINUTES
import javax.xml.datatype.DatatypeConstants.HOURS



//this class keeps track of the game state
//time left
//progress of the players

class GameState(var game : Game){
    var gameSettings = GameSettings.getInstance()
    var timer = object : CountDownTimer(gameSettings.getTimeControl().toLong(), 1000) {
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
    var count : Int = 0

    fun startTimer(){
        timer.start()
    }

    fun stopTimer(){
        timer.cancel()
    }

    fun addToCount(){
        count++
        game.problem_count.text = "solved: $count"
    }

}