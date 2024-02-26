package com.example.favoriteanimalwithradiobuttons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun radioButtonClick(view: View) {
        // check which radio button was clicked -- 'when' in Kotlin is like 'switch' in Java
        // Also, "when" statement is an expression that you can assign its result to a variable
        // For instance, if view.id is equal to R.id.dog, assign the value of R.drawable.dog to imageIdOfSelectedAnimal
        val imageIdOfSelectedAnimal = when (view.id) {
            R.id.dog -> R.drawable.dog
            R.id.cat -> R.drawable.cat
            R.id.bear -> R.drawable.bear
            else -> R.drawable.rabbit
        }
        // Set the id of the selected image to the image View
        findViewById<ImageView>(R.id.image_animal).setImageResource(imageIdOfSelectedAnimal)



        // The same functionality can be achieved using "if else" statements instead of "when" as follows:
        // However, from now on, we would prefer to use "when" as it is more convenient and concise
        /*        val imageIdOfSelectedAnimal: Int
                if(view.id == R.id.dog){
                    imageIdOfSelectedAnimal = R.drawable.dog
                }
                else if(view.id  == R.id.cat){
                    imageIdOfSelectedAnimal = R.drawable.cat
                }
                else if(view.id  == R.id.bear){
                    imageIdOfSelectedAnimal = R.drawable.bear
                }
                else {
                    imageIdOfSelectedAnimal = R.drawable.rabbit
                }
                findViewById<ImageView>(R.id.image_animal).setImageResource(imageIdOfSelectedAnimal)*/

    }
}