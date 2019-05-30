package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.wang.gates.quickmaths.classes.GameSettings
import com.wang.gates.quickmaths.R
import kotlinx.android.synthetic.main.game_start.*

class GameStart : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var difficulty : Int = GameSettings.EASY
    private val settings = GameSettings.getInstance()

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
            GameSettings.BOMB_MODE -> {
                game_start_title.text="Bomb Mode"
                description.text = "solve until you get one wrong"
            }
            GameSettings.TIMER_MODE -> {
                game_start_title.text="Timer Mode"
                description.text = "solve as many as you can"
            }
            GameSettings.CHALLENGE_FRIEND_MODE -> {
                game_start_title.text="Challenge a Friend"
                description.text = "beat your friends"
                //this is because we havent added this in yet
                start_game.setOnClickListener{
                    Toast.makeText(this@GameStart, "This feature is coming soon", Toast.LENGTH_SHORT)
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
                //difficulties
                difficulties[0] ->{
                    difficulty = GameSettings.EASY
                }
                difficulties[1] ->{
                    difficulty = GameSettings.MEDIUM
                }
                difficulties[2] ->{
                    difficulty = GameSettings.HARD
                }
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {

    }


}
