package com.wang.gates.quickmaths

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MenuInflater
import android.view.MenuItem


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        val layout = LinearLayout(this@MainActivity)
        val textView = TextView(this@MainActivity)
        textView.setText("" + answer)
        textView.textSize = 100f
        layout.addView(textView)
        builder.setView(layout)
        builder.setPositiveButton("Ok", DialogInterface.OnClickListener{dialog, _ ->
            dialog.dismiss()
            draw_view.newProblem()
        })
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.add -> {
                draw_view.setOperation(ProblemGenerator.ADD)
            }
            R.id.subtract -> {
                draw_view.setOperation(ProblemGenerator.SUBTRACT)
            }
            R.id.multiply -> {
                draw_view.setOperation(ProblemGenerator.MULTIPLY)
            }
            R.id.divide -> {
                draw_view.setOperation(ProblemGenerator.DIVIDE)
            }
            R.id.random -> {
                draw_view.setOperation(ProblemGenerator.RANDOM_OPERATION)
            }
            R.id.easy -> {
                draw_view.setDifficulty(ProblemGenerator.EASY)
            }
            R.id.medium -> {
                draw_view.setDifficulty(ProblemGenerator.MEDIUM)
            }
            R.id.hard -> {
                draw_view.setDifficulty(ProblemGenerator.HARD)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
