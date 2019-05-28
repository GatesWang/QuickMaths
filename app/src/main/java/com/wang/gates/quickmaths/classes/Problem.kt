package com.wang.gates.quickmaths.classes

class Problem(var problem : String){
    fun getProblemAnswer() : Int{
        val problemParts = getProblemParts()
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

    fun getProblemParts() : ArrayList<String>{
        val problemParts = arrayListOf<String>()
        for(operator in ProblemGenerator.operations){
            val operatorIndex = problem.indexOf(operator)
            if(operatorIndex!=-1){ // we found the right operator and its index
                problemParts.add(problem.substring(0,operatorIndex))
                problemParts.add(problem.substring(operatorIndex, operatorIndex+1))
                problemParts.add(problem.substring(operatorIndex+1,problem.length))
            }
        }
        return problemParts
    }

    override fun toString(): String {
        return problem
    }
}