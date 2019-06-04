package com.wang.gates.quickmaths.classes

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log

class Settings private constructor() {
    private var mode = Companion.Mode.TIMER_MODE
    private var difficulty = Companion.Difficulty.EASY
    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    companion object{
        enum class Mode(var int: Int){
            TIMER_MODE(0),
            BOMB_MODE(1),
            CHALLENGE_FRIEND_MODE(2)
        }

        enum class Difficulty(var int: Int){
            EASY(0),
            MEDIUM(1),
            HARD(2)
        }

        private val INSTANCE = Settings()
        fun getInstance() : Settings {
            return INSTANCE
        }
    }

    //saves high score for a particular mode and difficulty
    fun saveHighScore(context: Context, score : Int){
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = preferences!!.edit()
        editor!!.putInt("${getMode()}highscore${getDifficulty()}" ,score)
        editor!!.commit()
    }

    fun getHighScore(context: Context) : Int{
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences!!.getInt("${getMode()}highscore${getDifficulty()}", 0)
    }

    //gets high score for a particular mode and difficulty
    fun getHighScore(context: Context, mode: Int, difficulty: Int) : Int{
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences!!.getInt("${mode}highscore${difficulty}", 0)
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
        return Difficulty.valueOf(preferredDifficulty).int
    }

    fun setMode(mode : Mode){
        this.mode = mode
    }

    fun setDifficulty(difficulty : Difficulty){
        this.difficulty = difficulty
    }

    fun getMode() : Mode {
        return mode
    }

    fun getModeString() : String {
        when(mode){
            Mode.TIMER_MODE ->{
                return "Timer Mode"
            }
            else ->{
                return "Bomb Mode"
            }
        }
    }

    fun getDifficulty() : Difficulty {
        return difficulty
    }

    fun getDifficultyString() : String{
        when(difficulty){
            Companion.Difficulty.EASY ->{
                return "easy"
            }
            Companion.Difficulty.MEDIUM ->{
                return "medium"
            }
            else->{
                return "hard"
            }
        }
    }

}