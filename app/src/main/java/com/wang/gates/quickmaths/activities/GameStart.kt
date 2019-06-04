package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.wang.gates.quickmaths.classes.Settings
import com.wang.gates.quickmaths.R
import kotlinx.android.synthetic.main.game_start.*

class GameStart : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var difficulty : Settings.Companion.Difficulty = Settings.Companion.Difficulty.EASY
    private val settings = Settings.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_start)

        populateSpinners()
        setSpinnerBehavior()
        setButtonBehavior()
        setViews()
        setSpinnerSelected()
    }

    private fun setViews(){
        when(settings.getMode()){
            Settings.Companion.Mode.BOMB_MODE -> {
                game_start_title.text="Bomb Mode"
                description.text = "solve until you get one wrong"
            }
            Settings.Companion.Mode.TIMER_MODE -> {
                game_start_title.text="Timer Mode"
                description.text = "solve as many as you can"
            }
            Settings.Companion.Mode.CHALLENGE_FRIEND_MODE -> {
                game_start_title.text="Challenge a Friend"
                description.text = "beat your friends"
                //this is because we havent added this in yet
                start_game.setOnClickListener{
                    Toast.makeText(this@GameStart, "This feature is coming soon", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun populateSpinners(){
        ArrayAdapter.createFromResource(
            this,
            R.array.difficulties,
            R.layout.spinner_layout
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_layout)
            difficulty_spinner.adapter = adapter
        }
    }
    private fun setSpinnerBehavior(){
        difficulty_spinner.onItemSelectedListener = this
    }
    private fun setSpinnerSelected(){
        val preferredDifficulty = settings.getPreferredDifficulty(this@GameStart)
        if (preferredDifficulty != null ){
            difficulty_spinner.setSelection(preferredDifficulty)
        }
    }
    private fun setButtonBehavior(){
        start_game.setOnClickListener{
            settings.setDifficulty(difficulty)
            startActivity(Intent(this@GameStart, Game::class.java))
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val selected = parent.getItemAtPosition(pos)
        val difficulties = resources.getStringArray(R.array.difficulties)
        if(parent.getItemAtPosition(pos) is String){
            when(selected){
                difficulties[0] ->{
                    difficulty = Settings.Companion.Difficulty.EASY
                }
                difficulties[1] ->{
                    difficulty = Settings.Companion.Difficulty.MEDIUM
                }
                difficulties[2] ->{
                    difficulty = Settings.Companion.Difficulty.HARD
                }
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {

    }


}
