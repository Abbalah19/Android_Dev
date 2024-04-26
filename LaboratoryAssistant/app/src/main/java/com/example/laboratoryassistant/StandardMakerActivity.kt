package com.example.laboratoryassistant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class StandardMakerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard_maker)
        findViewById<TextView>(R.id.StandardResult_text).visibility = View.INVISIBLE

        val expandableText: TextView = findViewById<TextView>(R.id.standardGuide_text)
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

    fun calculate(view: View){
        hideKeyboard()
        val stockConcentrationText = findViewById<TextView>(R.id.StartingConc_editTextNumber).text.toString()
        val finalConcText = findViewById<TextView>(R.id.FinalConc_editTextNumber).text.toString()
        val finalVolText = findViewById<TextView>(R.id.FinalVolume_editTextNumber).text.toString()
        val resultText = findViewById<TextView>(R.id.StandardResult_text)

        if(stockConcentrationText.isNullOrEmpty() || finalConcText.isNullOrEmpty() || finalVolText.isNullOrEmpty()){
            resultText.text = "Please fill in all fields."
            resultText.visibility = View.VISIBLE
            return
        }

        val stockConcentration = stockConcentrationText.toDoubleOrNull()
        val finalConc = finalConcText.toDoubleOrNull()
        val finalVol = finalVolText.toDoubleOrNull()

        if(stockConcentration == null || finalConc == null || finalVol == null){
            resultText.text = "Please enter valid numbers."
            resultText.visibility = View.VISIBLE
            return
        }

        if(stockConcentration <= 0.0 || finalConc <= 0.0 || finalVol <= 0.0){
            resultText.text = "Please enter positive numbers."
            resultText.visibility = View.VISIBLE
            return
        }

        if(finalConc * finalVol > stockConcentration){
            resultText.text = "The combination of values entered is impossible."
            resultText.visibility = View.VISIBLE
            return
        }

        val vol = finalConc * finalVol / stockConcentration
        val volString = "%.3f".format(vol)

        val dil = finalVol - vol
        val dilString = "%.3f".format(dil)

        resultText.text = "Add $volString of the stock solution to a volumetric flask and dilute with $dilString DI or blank solution."
        resultText.visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun back(view: android.view.View) {
        finish()
    }
}