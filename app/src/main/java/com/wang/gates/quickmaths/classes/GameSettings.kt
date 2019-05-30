package com.wang.gates.quickmaths.classes

class GameSettings private constructor(){

    private var mode : Int? = null
    private var difficulty = EASY

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