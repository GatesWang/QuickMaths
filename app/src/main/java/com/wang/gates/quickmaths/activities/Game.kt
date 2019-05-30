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
import android.view.View.OnFocusChangeListener
import com.wang.gates.quickmaths.classes.ProblemGenerator
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.GameState
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.content.DialogInterface
import com.wang.gates.quickmaths.classes.GameSettings


class Game : AppCompatActivity() {
    private var generator = ProblemGenerator.getInstance()
    private var settings = GameSettings.getInstance()
    private var problem = generator.getProblem()
    private var gameState = GameState(this)
    private var input: EditText? = null
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)
        drawNewProblem()
        setListeners()
        gameState.createTimer()
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
        val builder = getBuilder()
        setInputParameters()
        builder.setPositiveButton("Submit"){dialog, _ ->
            dialog.dismiss()
            if(inputIsValid()){
                if(inputIsCorrect()){
                    Toast.makeText(this@Game, "correct", Toast.LENGTH_SHORT).show()
                    gameState.addToCount()
                    drawNewProblem()
                    if(settings.getMode()==GameSettings.BOMB_MODE){
                        gameState.stopTimer()
                        gameState.recalculateTime()
                        gameState.createTimer()
                        gameState.startTimer()
                    }
                }
                else{
                    Toast.makeText(this@Game, "incorrect", Toast.LENGTH_SHORT).show()
                    if(settings.getMode()==GameSettings.BOMB_MODE) {
                        finishGame()
                    }
                }
            }
            else{
                Toast.makeText(this@Game, "invalid input", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNeutralButton("Go back"){dialog, _ ->
            dialog.dismiss()
        }
        dialog = builder.create()
        setInputKeyBoard()
        dialog!!.show()
    }

    private fun getBuilder() : AlertDialog.Builder{
        val builder : AlertDialog.Builder  = AlertDialog.Builder(this@Game)
        builder.setTitle("Submit Answer")
        val layout = LinearLayout(this@Game)
        input = EditText(this@Game)
        layout.addView(input)
        builder.setView(layout)
        return builder
    }

    private fun setInputParameters(){
        input!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        input!!.inputType = InputType.TYPE_CLASS_NUMBER
        input!!.onFocusChangeListener = OnFocusChangeListener{ _,_ ->
            input!!.post{
                val inputMethodManager =
                    this@Game.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
            }
        }
        input!!.requestFocus()
    }

    private fun inputIsCorrect() : Boolean{
        return input!!.text.toString().toInt() == problem.getProblemAnswer()
    }

    private fun inputIsValid() : Boolean{
        val inputString = input!!.text.toString()
        val inputInt = inputString.toIntOrNull()
        return (inputInt!=null)
    }

    private fun setInputKeyBoard(){
        input!!.setOnEditorActionListener(OnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                dialog!!.getButton(DialogInterface.BUTTON_POSITIVE).performClick();
            }
            true
        })
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
