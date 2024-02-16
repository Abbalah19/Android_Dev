package com.example.layout_class3

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {
    // Updated in multiple methods
    private var runningScoreTally = 0
    private var totalRightAnswers = 0
    private var totalQuestionsAsked = 0
    private var operationValue = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createProblem()
    }

    // generates the random values and what the operation should be, passes info to
    // checkProblem method, may want to simply combine the methods together?
    private fun createProblem() {
        val num1 = generateRandomNumber(90,11)
        val num2 = generateRandomNumber(90, 10)
        operationValue = generateRandomNumber(4, 0)
        checkProblem(num1, num2)
    }

    /*
    Takes operation code and checks if the problem can be solved within constraints.
    subtraction >= 0 or division % = 0. If either fail the numbers will be re-rolled
    until the conditions are met then displayed on the screen.
     */
    private fun checkProblem(num1 : Int, num2 : Int) {

        // use to create new numbers for subtraction and division when constraints are not met
        // re-rolling an entirely new problem using function call made it so very few sub and div
        // problems showed up during testing. This way, each operation shows up about 25% of the time
        var numReplace1 = num1
        var numReplace2 = num2

        // op value of 0 = addition
        if ( operationValue == 0 || operationValue == 2){
            screenUpdate(numReplace1, numReplace2)
        }
        // op value of 1 = subtraction
        else if ( operationValue == 1 ) {
            while (numReplace1 - numReplace2 <= 0){
                numReplace1 = generateRandomNumber(90, 11)
                numReplace2 = generateRandomNumber(90, 10)
            }
            screenUpdate(numReplace1, numReplace2)
        }

        // extra or's are quick way to get more division questions, maybe change to loop to just keep
        // getting values? - similar approach used for subtraction
        else {
            while (numReplace1 % numReplace2 != 0){
                numReplace1 = generateRandomNumber(90, 11)
                numReplace2 = generateRandomNumber(90, 10)
            }
            screenUpdate(numReplace1, numReplace2)
        }
    }

    private fun screenUpdate(num1 : Int, num2 : Int){
        findViewById<TextView>(R.id.randomNumb_1Text).text = "$num1"
        findViewById<TextView>(R.id.randomNum_2Text).text = "$num2"
        if (operationValue == 0) {
            findViewById<android.widget.TextView>(com.example.layout_class3.R.id.resultText).text = "${num1 + num2}"
        }
        else if (operationValue == 1){
            findViewById<android.widget.TextView>(com.example.layout_class3.R.id.resultText).text = "${num1 - num2}"
        }
        else if (operationValue == 2){
            findViewById<android.widget.TextView>(com.example.layout_class3.R.id.resultText).text = "${num1 * num2}"
        }
        else {
            findViewById<android.widget.TextView>(com.example.layout_class3.R.id.resultText).text = "${num1 / num2}"
        }
    }

    /*
    waits for user input then checks if the answer is right or wrong. This check only
    compares the user button press to the corresponding operation value. It will increment, or
    reset score counters accordingly and display the corresponding right or wrong msg. It
    makes calls to the checkTally and createProblem functions to check if the user has a multiple
    of three right answers in a row and generates the next question.
     */
    fun checkAnswer( view : View) {
        val responseTextView = findViewById<TextView>(R.id.responseText)

        if (view.id == R.id.additionOpButton && operationValue == 0 ||
            view.id == R.id.subtrationOpButton && operationValue == 1 ||
            view.id == R.id.multiplyOpButton && operationValue == 2 ||
            view.id == R.id.divideOpButton && operationValue == 3)  {
            totalQuestionsAsked++
            totalRightAnswers++
            runningScoreTally++
            checkRunningTally()
            val correctMSG = "You are correct"
            responseTextView.setTextColor(Color.parseColor("#009933"))
            responseTextView.text = "${correctMSG}"
        }
        else {
            totalQuestionsAsked++
            runningScoreTally = 0
            var answer = '+'
            if (operationValue == 1){
                answer = '-'
            }
            else if (operationValue == 2){
                answer = 'X'
            }
            else if (operationValue == 3){
                answer = '÷'
            }
            val wrongMSG =  "Sorry, it was: ${findViewById<TextView>(R.id.randomNumb_1Text).text} ${answer}" +
                    " ${findViewById<TextView>(R.id.randomNum_2Text).text} = " +
                    "${findViewById<TextView>(R.id.resultText).text}"
            responseTextView.setTextColor(Color.parseColor("#cc0000"))
            responseTextView.text = "${wrongMSG}"
        }
        updateScores()
        createProblem()
    }

    private fun updateScores (){
        findViewById<TextView>(R.id.totalQuestionsText).text = "${totalQuestionsAsked}"
        findViewById<TextView>(R.id.rightAnswersText).text = "${totalRightAnswers}"
    }

    // Just checks if the user has answered a multiple of three right questions in a row
    private fun checkRunningTally() {
        if (runningScoreTally % 3 == 0){
            Toast.makeText(this, "You've got $runningScoreTally right in a row!!!", Toast.LENGTH_SHORT).show()
        }
    }

    // resets all score and tally counters and calls for a new question, clears response text as well
    fun resetGame(view : View) {
        totalRightAnswers = 0
        totalQuestionsAsked = 0
        runningScoreTally = 0
        findViewById<TextView>(R.id.responseText).text = " " // clear the response text
        updateScores()
        createProblem()
    }

    /*
    get random value with a upper bound plus a mod value e.g. 11 <= x <= 100. would be range 90 + modifier
    of 11. 0 is inclusive, range value is exclusive so if the call generates 0 -- 0 + 11 = 11 thus lower
    bound if the range is 90 exclusive then the max random number would be 89 so 89 + 11 = 100.
     */
    private fun generateRandomNumber(range: Int, modValue: Int): Int {
        return Random().nextInt(range) + modValue
    }
}