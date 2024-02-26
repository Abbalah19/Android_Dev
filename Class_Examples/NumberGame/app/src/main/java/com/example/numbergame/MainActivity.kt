package com.example.numbergame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class MainActivity : AppCompatActivity() {

    // To keep track the score of the user
    private var points = 0

    // Define a variable to represent the range of random numbers
    val randomNumberRange = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // helper function to pick 2 random numbers and display them
        pickRandomNumber()
    }




    // Instead of having three separate functions for each button click event (avoiding redundancy),
    // I use one function to perform the functionality of all three buttons. The if statement is used
    // to distinguish which button is clicked.
    fun checkCorrectness(view: View) {

        val message: String? // Declare a nullable String variable 'message'

        // Retrieve and convert each of the text from 'random_number1' and random_number2 TextViews to an integer
        val num1 = findViewById<TextView>(R.id.random_number1).text.toString().toInt()
        val num2 = findViewById<TextView>(R.id.random_number2).text.toString().toInt()


        // If the answer is correct, increment the score, else decrement the score by 1
        if ((view.id == R.id.less_than_button && num1 < num2) ||
            (view.id == R.id.greater_than_button && num1 > num2) ||
            (view.id == R.id.equal_button && num1 == num2)) {
            points++
            message = "Correct!"
        } else {
            points--
            message = "Wrong!!!"
        }

        // A little bit redundant (but easier to understand) version of the code above (if/else statement part)
        // If you already understand the code above, ignore this code block
        /*
        if (view.id == R.id.less_than_button && num1 < num2) {
            points++
            message = "Correct!"
        } else if (view.id == R.id.greater_than_button && num1 > num2) {
            points++
            message = "Correct!"
        } else if (view.id == R.id.equal_button && num1 == num2) {
            points++
            message = "Correct!"
        } else {
            points--
            message = "Wrong!!!"
        }
        */


        // Show a toast message as a feedback about whether the answer is correct or not
        Toast.makeText(this, "$message", Toast.LENGTH_SHORT).show()
        findViewById<TextView>(R.id.score).text = "Score: $points"

        // call the helper function again to set new random numbers
        pickRandomNumber()
    }

    // picks 2 random numbers and display them
    private fun pickRandomNumber() {
        // Generate random numbers, and
        // Set the random numbers to the textviews
        findViewById<TextView>(R.id.random_number1).text = "${generateRandomNumber(randomNumberRange)}"
        findViewById<TextView>(R.id.random_number2).text = "${generateRandomNumber(randomNumberRange)}"
    }

    // A helper function to generate a random number for a given upper bound (range) as a parameter
    private fun generateRandomNumber(range: Int): Int {
        return Random().nextInt(range)
    }
}