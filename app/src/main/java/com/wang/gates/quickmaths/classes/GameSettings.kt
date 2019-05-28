package com.wang.gates.quickmaths.classes

class GameSettings private constructor(){

    private var mode : Int? = null
    private var difficulty = EASY
    private var timeControl = ONE_MINUTE

    companion object{
        val TIMER_MODE = 0
        val BOMB_MODE = 1
        val CHALLENGE_FRIEND_MODE = 2

        val EASY = 0
        val MEDIUM = 1
        val HARD = 2

        val ONE_MINUTE = 0
        val TWO_MINUTES = 1
        val THREE_MINUTES = 2

        val FIVE_SECONDS = 3
        val TEN_SECONDS = 4
        val FIFTEEN_SECONDS = 5

        private val INSTANCE = GameSettings()
        fun getInstance() : GameSettings {
            return INSTANCE
        }
    }

    fun setMode(mode : Int){
        this.mode = mode
    }

    fun setDifficulty(difficulty : Int){
        this.difficulty = difficulty
    }

    fun setTimeControl(timeControl : Int){
        this.timeControl = timeControl
    }

    fun getMode() : Int {
        return mode!!
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

    fun getTimeControl() : Int {
        return timeControl
    }

    //returns "" for a time control that is not valid
    fun getTimeControlString() : String{
        when(timeControl){
            ONE_MINUTE ->{
            return "1:00"
        }
            TWO_MINUTES ->{
                return "2:00"
            }
            THREE_MINUTES->{
                return "3:00"
            }
            FIVE_SECONDS ->{
                return "0:05"
            }
            TEN_SECONDS ->{
                return "0:10"
            }
            FIFTEEN_SECONDS->{
                return "0:15"
            }
        }
        return ""
    }

    override fun toString(): String {
        return "mode" + getMode() + "difficulty" + getDifficulty() + "time" + getTimeControl()
    }
}