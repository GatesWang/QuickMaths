package com.wang.gates.quickmaths

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.Menu
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.view.View.OnFocusChangeListener


class MainActivity : AppCompatActivity() {
    private var mainMenu : Menu? = null
    private var scoreTracker : ScoreTracker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scoreTracker = ScoreTracker(this@MainActivity, score)
        setListeners()
    }

    private fun setListeners(){
        clear_canvas_button.setOnClickListener{
            draw_view.clearPath()
        }
        new_problem.setOnClickListener{
            draw_view.newProblem()
        }
        show_answer.setOnClickListener{
            showAnswerDialog(draw_view.getAnswer())
        }
    }

    private fun showAnswerDialog(answer : Int){
        val builder : AlertDialog.Builder  = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Submit Answer")
        val layout = LinearLayout(this@MainActivity)
        val input = EditText(this@MainActivity)
        input.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        input.inputType = InputType.TYPE_CLASS_NUMBER
        input.setOnFocusChangeListener(object : OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                input.post(Runnable {
                    val inputMethodManager =
                        this@MainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
                })
            }
        })
        input.requestFocus()
        layout.addView(input)
        builder.setView(layout)

        builder.setPositiveButton("Submit", DialogInterface.OnClickListener{dialog, _ ->
            dialog.dismiss()
            if(input.text.toString().toInt().equals(answer)){
                scoreTracker!!.increaseScore(draw_view.getDifficulty())
                Toast.makeText(this@MainActivity, "correct", Toast.LENGTH_SHORT).show()
                draw_view.newProblem()
            }
            else{
                Toast.makeText(this@MainActivity, "incorrect", Toast.LENGTH_SHORT).show()
            }
        })
        builder.setNeutralButton("Go back", DialogInterface.OnClickListener{dialog, _ ->
            dialog.dismiss()
        })

        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        mainMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.add -> {
                draw_view.setOperation(ProblemGenerator.ADD)
                mainMenu!!.findItem(R.id.operation).title = "add"
            }
            R.id.subtract -> {
                draw_view.setOperation(ProblemGenerator.SUBTRACT)
                mainMenu!!.findItem(R.id.operation).title = "subtract"
            }
            R.id.multiply -> {
                draw_view.setOperation(ProblemGenerator.MULTIPLY)
                mainMenu!!.findItem(R.id.operation).title = "multiply"
            }
            R.id.divide -> {
                draw_view.setOperation(ProblemGenerator.DIVIDE)
                mainMenu!!.findItem(R.id.operation).title = "divide"
            }
            R.id.random -> {
                draw_view.setOperation(ProblemGenerator.RANDOM_OPERATION)
                mainMenu!!.findItem(R.id.operation).title = "random"
            }
            R.id.easy -> {
                draw_view.setDifficulty(ProblemGenerator.EASY)
                mainMenu!!.findItem(R.id.difficulty).title = "easy"
            }
            R.id.medium -> {
                draw_view.setDifficulty(ProblemGenerator.MEDIUM)
                mainMenu!!.findItem(R.id.difficulty).title = "medium"
            }
            R.id.hard -> {
                draw_view.setDifficulty(ProblemGenerator.HARD)
                mainMenu!!.findItem(R.id.difficulty).title = "hard"
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
