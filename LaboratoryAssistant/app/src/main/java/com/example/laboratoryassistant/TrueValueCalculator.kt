package com.example.laboratoryassistant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class TrueValueCalculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_true_value_calculator)
        findViewById<TextView>(R.id.TrueValueResult).visibility = View.INVISIBLE

        val expandableText: TextView = findViewById<TextView>(R.id.True_Value_Info_Block)
        expandableText.setOnClickListener {
            if (expandableText.maxLines == Integer.MAX_VALUE) {
                // If the text is currently expanded, collapse it back to 3 lines
                expandableText.maxLines = 3
            } else {
                // If the text is collapsed, expand it
                expandableText.maxLines = Integer.MAX_VALUE
            }
        }
    }

    fun calculate(view:View){
        hideKeyboard()
        val startConc = findViewById<TextView>(R.id.TrueValueStartConc).text.toString()
        val spikeAmt = findViewById<TextView>(R.id.TrueValueSpikeAmt).text.toString()
        val finalVol = findViewById<TextView>(R.id.TrueValueFinalVol).text.toString()

        if (startConc.isNullOrEmpty() || spikeAmt.isNullOrEmpty() || finalVol.isNullOrEmpty()){
            findViewById<TextView>(R.id.TrueValueResult).text = "Please fill in all fields."
            findViewById<TextView>(R.id.TrueValueResult).visibility = View.VISIBLE
            return
        }
        else if (startConc.toDouble() <= 0.0 || spikeAmt.toDouble() <= 0.0 || finalVol.toDouble() <= 0.0){
            findViewById<TextView>(R.id.TrueValueResult).text = "Please enter positive numbers."
            findViewById<TextView>(R.id.TrueValueResult).visibility = View.VISIBLE
            return
        }

        val startingConc = startConc.toDouble()
        val spikeAmount = spikeAmt.toDouble()
        val finalVolume = finalVol.toDouble()
        val trueValue = (startingConc * spikeAmount) / finalVolume
        val trueValueString = "%.3f".format(trueValue)

        findViewById<TextView>(R.id.TrueValueResult).text = "The value of the spike inside the sample is $trueValueString."
        findViewById<TextView>(R.id.TrueValueResult).visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun back(view: android.view.View) {
        finish()
    }

}