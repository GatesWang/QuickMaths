package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.game.*
import android.widget.Toast
import com.wang.gates.quickmaths.classes.ProblemGenerator
import com.wang.gates.quickmaths.R
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.ProgressBar
import com.wang.gates.quickmaths.classes.Problem
import com.wang.gates.quickmaths.classes.Settings
import com.wang.gates.quickmaths.classes.State
import kotlinx.android.synthetic.main.submit_answer.*


class Game : AppCompatActivity() {
    private val generator = ProblemGenerator.getInstance()
    private val settings = Settings.getInstance()
    private var problem = generator.getProblem()
    private var gameState : State? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        settings.savePreferredDifficulty(this@Game)
        setProgressBarColor(progress_bar)
        drawNewProblem()
        gameState = State(this)
        gameState!!.createTimer()
        gameState!!.startTimer()
        setListeners()

    }

    private fun setProgressBarColor(progress: ProgressBar,
                                    @ColorInt color: Int = ContextCompat.getColor(progress.context, R.color.colorPrimaryDark)){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            progress.progressTintList = ColorStateList.valueOf(color)
        } else{
            val layerDrawable = progress.progressDrawable as? LayerDrawable
            val progressDrawable = layerDrawable?.findDrawableByLayerId(android.R.id.progress)
            progressDrawable?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    private fun setListeners(){
        clear_canvas.setOnClickListener{
            draw_view_problem.clearPath()
        }
        submit.setOnClickListener{
            submitAnswer()
        }
    }

    private fun submitAnswer(){
        if(!inputIsValid()) {
            Toast.makeText(this@Game, "invalid input", Toast.LENGTH_SHORT).show()
            input.setText("")
            return
        }

        if(inputIsCorrect()){
            gameState!!.addToCount()
            input.setText("")
            drawNewProblem()
            ifBombModeLowerTime()
        }
        else{
            Toast.makeText(this@Game, "incorrect", Toast.LENGTH_SHORT).show()
            input.setText("")
        }
    }

    private fun ifBombModeLowerTime(){
        if(settings.getMode()== Settings.Companion.Mode.BOMB_MODE) {
            gameState!!.stopTimer()
            gameState!!.recalculateTime()
            gameState!!.createTimer()
            gameState!!.startTimer()
        }
    }

    private fun inputIsValid() : Boolean{
        val inputString = input.text.toString().trim()
        val inputInt = inputString.toIntOrNull()
        return (inputInt!=null)
    }

    private fun inputIsCorrect() : Boolean{
        return input.text.toString().toInt() == problem.getProblemAnswer()
    }

    private fun drawNewProblem(){
        problem = generator.getProblem()
        draw_view_problem.setProblem(problem)
        draw_view_problem.clearPath()
    }

    fun finishGame(){
        //get ready to go to game score
        val intent = Intent(this@Game, GameScore::class.java)
        intent.putExtra("score", gameState!!.score)
        startActivity(intent)
        //close
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        //stop the timer
        gameState!!.stopTimer()
    }


}