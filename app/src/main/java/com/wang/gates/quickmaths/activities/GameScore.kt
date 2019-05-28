package com.wang.gates.quickmaths.activities

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.GameSettings
import android.widget.TextView
import android.view.LayoutInflater
import android.content.Context
import android.content.Intent
import com.wang.gates.quickmaths.activities.modes.GameLocalPlayer
import com.wang.gates.quickmaths.activities.modes.GameMultiplayer
import com.wang.gates.quickmaths.activities.modes.GameSinglePlayer
import kotlinx.android.synthetic.main.game_score.*


//activity to display the results of the game
//shows previous high scores
//can go back to main menu
//or play again

class GameScore : AppCompatActivity() {
    private var preferences : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null
    private var settings = GameSettings.getInstance()

    private var score : Int? = null
    private var previousHighScore : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_score)

        fillNullValues()
        fillViews()
        checkForNewHighScore()
        setButtonBehavior()
    }

    private fun fillNullValues(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this@GameScore)
        editor = preferences!!.edit()
        previousHighScore = preferences!!.getInt(settings.toString(),0)
        score = intent.getIntExtra("score",0)
    }

    private fun fillViews(){
        settings_text_view.text = settings.getDifficultyString() +  " " + settings.getTimeControlString()
        score_text_view.text = "score : $score"
        previous_high_score_text_view.text = "previous high score: $previousHighScore"
    }

    private fun checkForNewHighScore(){
        if(score!! > previousHighScore!!){
            //new high score
            showNewHighScore()
            editor!!.putInt(settings.toString(), score!!)
            editor!!.commit()
        }
    }
    //if there is a new high score, change views
    private fun showNewHighScore(){
        score_text_view.text = "new high score: $score"
    }

    private fun setButtonBehavior(){
        main_menu_button.setOnClickListener{
            finish()
        }
        play_again_button.setOnClickListener{
            when(settings.getMode()){
                GameSettings.SINGLE_PLAYER ->{
                    startActivity(Intent(this@GameScore, GameSinglePlayer::class.java))
                }
                GameSettings.MUlTIPLAYER ->{
                    startActivity(Intent(this@GameScore, GameMultiplayer::class.java))
                }
                GameSettings.LOCAL_PLAYER ->{
                    startActivity(Intent(this@GameScore, GameLocalPlayer::class.java))
                }
            }
            finish()
        }
    }
}
