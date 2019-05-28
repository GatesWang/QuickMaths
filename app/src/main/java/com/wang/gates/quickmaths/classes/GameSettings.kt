package com.wang.gates.quickmaths.classes

class GameSettings private constructor(){

    private var mode : Int? = null
    private var difficulty = EASY
    private var timeControl = ONE_MINUTE

    companion object{
        val SINGLE_PLAYER = 0
        val MUlTIPLAYER = 1
        val LOCAL_PLAYER = 2

        val EASY = 0
        val MEDIUM = 1
        val HARD = 2

        val ONE_MINUTE = 60000
        val TWO_MINUTES = 120000
        val THREE_MINUTES = 180000

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
        return timeControl!!
    }

    fun getTimeControlString() : String{
        when(timeControl){
            ONE_MINUTE ->{
                return "1:00"
            }
            TWO_MINUTES ->{
                return "2:00"
            }
            else->{
                return "3:00"
            }
        }
    }

    override fun toString(): String {
        return "mode" + getMode() + "difficulty" + getDifficulty() + "time" + getTimeControl()
    }
}