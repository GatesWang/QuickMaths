package com.wang.gates.quickmaths.activities

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.GameSettings
import android.content.Intent
import com.wang.gates.quickmaths.activities.modes.BombMode
import com.wang.gates.quickmaths.activities.modes.ChallengeFriendMode
import com.wang.gates.quickmaths.activities.modes.TimerMode
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
        savePreferredGameSettings()
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
            editor!!.putBoolean("playagain",false)
            finish()
        }
        play_again_button.setOnClickListener{
            when(settings.getMode()){
                GameSettings.TIMER_MODE ->{
                    startActivity(Intent(this@GameScore, TimerMode::class.java))
                }
                GameSettings.BOMB_MODE ->{
                    startActivity(Intent(this@GameScore, BombMode::class.java))
                }
                GameSettings.CHALLENGE_FRIEND_MODE ->{
                    startActivity(Intent(this@GameScore, ChallengeFriendMode::class.java))
                }
            }
            finish()
        }
    }

    //for this particular mode save the game settings
    private fun savePreferredGameSettings(){
        editor!!.putString(settings.getMode().toString() + "difficulty", settings.getDifficulty().toString())
        editor!!.putString(settings.getMode().toString() + "timecontrol", settings.getTimeControl().toString())
        editor!!.commit()
    }
}
