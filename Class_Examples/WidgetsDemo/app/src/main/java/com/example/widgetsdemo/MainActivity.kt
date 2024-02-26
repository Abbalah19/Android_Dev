package com.example.widgetsdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekBarLabel = findViewById<TextView>(R.id.seek_bar_label)

        // Listen seekBar change events: There are three override methods that must be implemented
        // though you may not necessarily use the last two methods (so do not delete them)
        findViewById<SeekBar>(R.id.seek_bar_age).setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // As the seekbar moves, the progress value is obtained and displayed in our seekBar label
                seekBarLabel.text = "Age: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }


    // This function runs when the button is clicked
    fun showInfo(view: View) {
        // Get user input from the EditTexts
        val personName = findViewById<TextView>(R.id.name)
        val personAge = findViewById<TextView>(R.id.age)

        if (personAge.text.toString().toIntOrNull() == null) {
            Toast.makeText(this, "Please enter your age", Toast.LENGTH_SHORT).show()
            return
        }

        val feedback = findViewById<TextView>(R.id.feedback)

        // Finally display the information
        feedback.text = "Hello ${personName.text}, your age is ${personAge.text}"

        // Clear the EditTexts
        personName.text = ""
        personAge.text = ""

        // Hide the keyboard after entering age
        personAge.hideKeyboard()

    }

    // Helper function to hide the keyboard for any view/widget
    private fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun radioButtonClick(view: View) {
        // check which radio button was clicked
        val message: String
        if (view.id == R.id.male){
            message = "Male"
        } else if (view.id == R.id.female){
            message = "Female"
        } else {
            message = "Other"
        }

        // Make a toast message to show
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        // setting toast at center with a negative y offset value so that it appear at a different part of the screen
        toast.setGravity(Gravity.CENTER, 0, -440)
        toast.show()
    }

    fun checkBoxClick(view: View) {
        // check which checkbox was Selected or Deselected
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.car -> {
                    if (checked) {
                        // Checked car - do something
                    } else {
                        // Unchecked car - do something
                    }
                }
                R.id.bus -> {
                    if (checked) {
                        // Checked bus - do something
                    } else {
                        // Unchecked bus - do something
                    }
                }
            }
        }
    }

    fun switchClick(view: View) {
        // check if switch is checked
        val switchRent = findViewById<Switch>(R.id.switch_rent)
        val switchText: String
        if (switchRent.isChecked) {
            switchText = "Rent"
        } else {
            switchText = "Not Rent"
        }
        // show a custom message on the switch
        switchRent.text = switchText
    }
}