package com.wang.gates.quickmaths.activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.game.*
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.view.View.OnFocusChangeListener
import com.wang.gates.quickmaths.classes.ProblemGenerator
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.GameState

class Game : AppCompatActivity() {
    private var generator = ProblemGenerator.getInstance()
    private var problem = generator.getProblem()
    private var gameState = GameState(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        drawNewProblem()
        setListeners()
        gameState.startTimer()
    }

    private fun setListeners(){
        clear_canvas_button.setOnClickListener{
            draw_view.clearPath()
        }
        show_answer.setOnClickListener{
            showSubmitAnswerDialog()
        }
    }

    private fun showSubmitAnswerDialog(){
        val builder : AlertDialog.Builder  = AlertDialog.Builder(this@Game)
        builder.setTitle("Submit Answer")
        val layout = LinearLayout(this@Game)
        val input = EditText(this@Game)
        input.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.onFocusChangeListener = OnFocusChangeListener{ _,_ ->
            input.post{
                val inputMethodManager =
                    this@Game.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        input.requestFocus()
        layout.addView(input)
        builder.setView(layout)

        builder.setPositiveButton("Submit"){dialog, _ ->
            dialog.dismiss()
            if(input.text.toString().toInt().equals(problem.getProblemAnswer())){
                Toast.makeText(this@Game, "correct", Toast.LENGTH_SHORT).show()
                gameState.addToCount()
                drawNewProblem()
            }
            else{
                Toast.makeText(this@Game, "incorrect", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNeutralButton("Go back"){dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun drawNewProblem(){
        problem = generator.getProblem()
        draw_view.setProblem(problem)
        draw_view.clearPath()
    }

    fun finishGame(){
        val intent = Intent(this@Game, GameScore::class.java)
        intent.putExtra("score", gameState.count)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        gameState.stopTimer()
    }



}
