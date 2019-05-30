package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.game.*
import android.widget.EditText
import android.widget.Toast
import com.wang.gates.quickmaths.classes.ProblemGenerator
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.GameState
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.widget.ProgressBar
import com.wang.gates.quickmaths.classes.GameSettings

class Game : AppCompatActivity() {
    private var generator = ProblemGenerator.getInstance()
    private var settings = GameSettings.getInstance()
    private var problem = generator.getProblem()
    private var gameState : GameState? = null
    private var input: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        setProgressBarColor(progressBar)
        drawNewProblem()
        setListeners()

        gameState = GameState(this)
        gameState!!.createTimer()
        gameState!!.startTimer()
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
            draw_view_answer.clearPath()
        }
        submit_answer.setOnClickListener{
//            submitAnswer()
        }
    }

    private fun submitAnswer(){
        if(!inputIsValid()) {
            Toast.makeText(this@Game, "invalid input", Toast.LENGTH_SHORT).show()
            return
        }

        if(inputIsCorrect()){
            Toast.makeText(this@Game, "correct", Toast.LENGTH_SHORT).show()
            gameState!!.addToCount()
            drawNewProblem()
            ifBombModeLowerTime()
        }
        else{
            Toast.makeText(this@Game, "incorrect", Toast.LENGTH_SHORT).show()
            ifBombModeFinishGame()
        }
    }

    private fun ifBombModeLowerTime(){
        if(settings.getMode()==GameSettings.BOMB_MODE) {
            gameState!!.stopTimer()
            gameState!!.recalculateTime()
            gameState!!.createTimer()
            gameState!!.startTimer()
        }
    }

    private fun inputIsValid() : Boolean{
        val inputString = input!!.text.toString()
        val inputInt = inputString.toIntOrNull()
        return (inputInt!=null)
    }

    private fun inputIsCorrect() : Boolean{
        return input!!.text.toString().toInt() == problem.getProblemAnswer()
    }

    private fun drawNewProblem(){
        problem = generator.getProblem()
        draw_view_problem.setProblem(problem)
        draw_view_problem.clearPath()
    }

    fun ifBombModeFinishGame(){
        if(settings.getMode()==GameSettings.BOMB_MODE) {
            val intent = Intent(this@Game, GameScore::class.java)
            intent.putExtra("score", gameState!!.score)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        gameState!!.stopTimer()
    }



}
