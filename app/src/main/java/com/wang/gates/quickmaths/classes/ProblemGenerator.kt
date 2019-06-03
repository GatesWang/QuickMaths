package com.wang.gates.quickmaths.classes

import kotlin.random.Random
class ProblemGenerator private constructor(){
    companion object {
        private val INSTANCE = ProblemGenerator()
        val operations = arrayOf("+", "-", "*", "/")

        fun getInstance() : ProblemGenerator {
            return INSTANCE
        }
    }

    fun getProblem() : Problem {
        val difficulty = Settings.getInstance().getDifficulty()
        val operationString = getRandomOperation()
        val operands = getOperands(difficulty, operationString)
        return Problem("${operands[0]}$operationString${operands[1]}")
    }

    private fun getRandomOperation() : String {
        val randomValue = Random.nextInt(0, operations.size)
        return operations[randomValue]
    }

    private fun getOperands(difficulty: Int, operation : String) : ArrayList<Int>{
        var operands = arrayListOf<Int>()
        when(operation){
            "+" -> {
                operands = getOperandsAdd(difficulty)
            }
            "-" -> {
                operands = getOperandsSubtract(difficulty)
            }
            "*" -> {
                operands = getOperandsMultiply(difficulty)
            }
            "/" -> {
                operands = getOperandsDivide(difficulty)
            }
        }
        return operands
    }

    private fun getOperandsMultiply(difficulty : Int) : ArrayList<Int>{
        val operands = arrayListOf<Int>()
        when(difficulty){
            Settings.EASY ->{
                operands.add(getRandomNumber(1))
                operands.add(getRandomNumber(1))
            }
            Settings.MEDIUM -> {
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(1))
            }
            Settings.HARD ->{
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(2))
            }
        }
        return operands
    }

    private fun getOperandsDivide(difficulty : Int) : ArrayList<Int>{
        val operands = arrayListOf<Int>()
        when(difficulty){
            Settings.EASY ->{
                var first = getRandomNumber(1)
                var second = getRandomNumber(1)
                operands.add(first*second)
                operands.add(first)
            }
            Settings.MEDIUM -> {
                var first = getRandomNumber(1) * getRandomNumber(1)* getRandomNumber(1)
                var second = getRandomNumber(1)
                operands.add(first*second)
                operands.add(second)
            }
            Settings.HARD ->{
                var first = getRandomNumber(2) * getRandomNumber(1)
                var second = getRandomNumber(1)
                operands.add(first*second)
                operands.add(second)
            }
        }
        return operands
    }

    private fun getOperandsSubtract(difficulty : Int) : ArrayList<Int>{
        var operands = arrayListOf<Int>()
        when(difficulty){
            Settings.EASY ->{
                operands.add(getRandomNumber(1))
                operands.add(getRandomNumber(1))
            }
            Settings.MEDIUM -> {
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(2))
            }
            Settings.HARD ->{
                operands.add(getRandomNumber(3))
                operands.add(getRandomNumber(3))
            }
        }
        return ArrayList(operands.sortedWith(compareBy{ -it }))
    }

    private fun getOperandsAdd(difficulty : Int) : ArrayList<Int>{
        val operands = arrayListOf<Int>()
        when(difficulty){
            Settings.EASY ->{
                operands.add(getRandomNumber(1))
                operands.add(getRandomNumber(1))
            }
            Settings.MEDIUM -> {
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(2))
            }
            Settings.HARD ->{
                operands.add(getRandomNumber(3))
                operands.add(getRandomNumber(3))
            }
        }
        return operands
    }

    //N is the number of digits
    //the number returned cannot be one or a power of 10
    private fun getRandomNumber(N : Int) : Int {
        val maxValue = Math.pow(10.0, N.toDouble()).toInt()
        val minValue = Math.pow(10.0, (N-1).toDouble()).toInt()
        return Random.nextInt(minValue+1, maxValue-1)
    }

}