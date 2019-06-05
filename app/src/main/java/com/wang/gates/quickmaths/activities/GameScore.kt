package com.wang.gates.quickmaths.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.Settings
import android.content.Intent
import android.util.Log
import kotlinx.android.synthetic.main.game_score.*
import org.jetbrains.anko.*


//activity to display the results of the game
//shows previous high scores
//can go back to main menu
//or play again

class GameScore : AppCompatActivity() {
    private var settings = Settings.getInstance()

    private var score : Int? = null
    private var previousHighScore : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_score)

        fillNullValues()
        fillViews()
        checkForNewHighScore()
        setButtonBehavior()
        settings.savePreferredDifficulty(this@GameScore)
    }

    private fun fillNullValues(){
        previousHighScore = settings.getHighScore(this@GameScore)
        score = intent.getIntExtra("score",0)
    }

    private fun fillViews(){
        settings_text_view.text = "" + settings.getModeString() + " " + settings.getDifficultyString()
        score_text_view.text = "score : $score"
        previous_high_score_text_view.text = "previous high score: $previousHighScore"
    }

    private fun checkForNewHighScore(){
        if(score!! > previousHighScore!!){
            //new high score
            showNewHighScore()
            Log.d(">>>", "high score, $score")
            settings.saveHighScore(this@GameScore, score!!)
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
                Settings.Companion.Mode.TIMER_MODE ->{
                    startActivity(Intent(this@GameScore, GameStart::class.java))
                }
                Settings.Companion.Mode.BOMB_MODE ->{
                    startActivity(Intent(this@GameScore, GameStart::class.java))
                }
            }
            finish()
        }

    }

}
