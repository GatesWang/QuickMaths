package com.wang.gates.quickmaths

import android.util.Log
import java.lang.IllegalArgumentException
import kotlin.random.Random
class ProblemGenerator {
    companion object {
        val ADD = 0
        val SUBTRACT = 1
        val MULTIPLY = 2
        val DIVIDE = 3
        val RANDOM_OPERATION = 4

        val EASY = 5
        val MEDIUM = 6
        val HARD = 7
    }

    private val operations = arrayOf("+", "-", "*", "/")

    fun getProblemAnswer(problemParts : ArrayList<String>) : Int{
        var answer = 0
        val op1 = problemParts[0].toInt()
        val op2 = problemParts[2].toInt()
        val operation = problemParts[1]
        when(operation){
            "+" -> {
                answer = op1 + op2
            }
            "-" -> {
                answer = op1 - op2
            }
            "*" -> {
                answer = op1 * op2
            }
            "/" -> {
                answer = op1 / op2
            }
        }
        return answer
    }

    fun getProblemParts(problem : String) : ArrayList<String>{
        val problemParts = arrayListOf<String>()
        for(operator in operations){
            val operatorIndex = problem.indexOf(operator)
            if(operatorIndex!=-1){ // we found the right operator and its index
                problemParts.add(problem.substring(0,operatorIndex))
                problemParts.add(problem.substring(operatorIndex, operatorIndex+1))
                problemParts.add(problem.substring(operatorIndex+1,problem.length))
            }
        }
        return problemParts
    }

    fun getProblem(operation : Int, difficulty : Int) : String{
        checkIfOperationIsValid(operation)
        checkIfDifficultyIsValid(difficulty)
        val operationString = operationToStringForm(operation)
        val operands = getOperands(difficulty, operationString)
        return "${operands[0]}$operationString${operands[1]}"
    }

    private fun checkIfOperationIsValid(operation : Int){
        if(!(operation in 0..4)){
            throw IllegalArgumentException("operation must be between 0 and 4 inclusive")
        }
    }

    private fun checkIfDifficultyIsValid(difficulty : Int){
        if(!(difficulty in 5..7)){
            throw IllegalArgumentException("difficulty must be between 5 and 7 inclusive")
        }
    }

    private fun operationToStringForm(operation : Int) : String {
        return if(operation==RANDOM_OPERATION) getRandomOperation() else operations[operation]
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
            EASY ->{
                operands.add(getRandomNumber(1))
                operands.add(getRandomNumber(1))
            }
            MEDIUM -> {
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(1))
            }
            HARD ->{
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(2))
            }
        }
        return operands
    }

    private fun getOperandsDivide(difficulty : Int) : ArrayList<Int>{
        val operands = arrayListOf<Int>()
        when(difficulty){
            EASY ->{
                var first = getRandomNumber(1)
                var second = getRandomNumber(1)
                operands.add(first*second)
                operands.add(first)
            }
            MEDIUM -> {
                var first = getRandomNumber(1) * getRandomNumber(1)* getRandomNumber(1)
                var second = getRandomNumber(1)
                operands.add(first*second)
                operands.add(second)
            }
            HARD ->{
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
            EASY ->{
                operands.add(getRandomNumber(1))
                operands.add(getRandomNumber(1))
            }
            MEDIUM -> {
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(2))
            }
            HARD ->{
                operands.add(getRandomNumber(3))
                operands.add(getRandomNumber(3))
            }
        }
        return ArrayList(operands.sortedWith(compareBy({ -it })))
    }

    private fun getOperandsAdd(difficulty : Int) : ArrayList<Int>{
        val operands = arrayListOf<Int>()
        when(difficulty){
            EASY ->{
                operands.add(getRandomNumber(1))
                operands.add(getRandomNumber(1))
            }
            MEDIUM -> {
                operands.add(getRandomNumber(2))
                operands.add(getRandomNumber(2))
            }
            HARD ->{
                operands.add(getRandomNumber(3))
                operands.add(getRandomNumber(3))
            }
        }
        return operands
    }

    //N is the number of digits
    //the number returned cannot be one
    private fun getRandomNumber(N : Int) : Int {
        val maxValue = Math.pow(10.0, N.toDouble()).toInt()
        val minValue = Math.pow(10.0, (N-1).toDouble()).toInt()
        val returnVal = Random.nextInt(minValue, maxValue)
        if (returnVal==1) return getRandomNumber(N) else return returnVal
    }

}