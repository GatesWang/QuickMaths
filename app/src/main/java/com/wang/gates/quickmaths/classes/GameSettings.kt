package com.wang.gates.quickmaths.classes

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class GameSettings private constructor() {
    private var mode: Int? = null
    private var difficulty = EASY
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    companion object{
        val TIMER_MODE = 0
        val BOMB_MODE = 1
        val CHALLENGE_FRIEND_MODE = 2

        val EASY = 0
        val MEDIUM = 1
        val HARD = 2

        private val INSTANCE = GameSettings()
        fun getInstance() : GameSettings {
            return INSTANCE
        }
    }

    //saves high score for a particular mode and difficulty
    fun saveHighScore(context: Context, score : Int){
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences!!.edit()
        editor!!.putInt("highscore",score)
        editor!!.commit()
    }

    //gets high score for a particular mode and difficulty
    fun getHighScore(context: Context) : Int{
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences!!.getInt("highscore", 0)
    }

    //for this particular mode save difficulty as the preferred difficulty
    fun savePreferredDifficulty(context: Context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences!!.edit()
        editor!!.putString(getMode().toString(), getDifficulty().toString())
        editor!!.commit()
    }

    //for this particular mode get the preferred difficulty
    fun getPreferredDifficulty(context: Context) : Int {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val preferredDifficulty = preferences!!.getString(getMode().toString(), getDifficulty().toString())
        return preferredDifficulty.toInt()
    }

    fun setMode(mode : Int){
        this.mode = mode
    }

    fun setDifficulty(difficulty : Int){
        this.difficulty = difficulty
    }

    fun getMode() : Int {
        return mode!!
    }

    fun getModeString() : String {
        when(mode!!){
            TIMER_MODE ->{
                return "Timer Mode"
            }
            else ->{
                return "Bomb Mode"
            }
        }
    }

    fun getDifficulty() : Int {
        return difficulty
    }

    fun getDifficultyString() : String{
        when(difficulty){
            EASY ->{
                return "easy"
            }
            MEDIUM ->{
                return "medium"
            }
            else->{
                return "hard"
            }
        }
    }

}