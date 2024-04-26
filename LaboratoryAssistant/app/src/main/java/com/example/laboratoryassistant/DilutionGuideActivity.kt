package com.example.laboratoryassistant

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

class DilutionGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dilution_guide)
    }

    fun calculate(view: View){
        hideKeyboard()
        val dilutionNumberText = findViewById<TextView>(R.id.dilutionNumber).text.toString()
        val finalNumberText = findViewById<TextView>(R.id.finalNumber).text.toString()
        val resultText = findViewById<TextView>(R.id.dilutionResult_textView)

        if(dilutionNumberText.isNullOrEmpty() || finalNumberText.isNullOrEmpty()){
            resultText.text = "Please fill in all fields."
            resultText.visibility = View.VISIBLE
            return
        }

        val dilutionfactor = dilutionNumberText.toDouble()
        val finalVolume = finalNumberText.toDouble()

        if(dilutionfactor == 0.0 || finalVolume == 0.0){
            resultText.text = "Please enter valid numbers."
            resultText.visibility = View.VISIBLE
            return
        }
        else if(dilutionfactor < 0.0 || finalVolume < 0.0){
            resultText.text = "Please enter positive numbers."
            resultText.visibility = View.VISIBLE
            return
        }

        val dil = finalVolume / dilutionfactor
        val dilString = "%.3f".format(dil)

        val vol = finalVolume - dil
        val volString = "%.3f".format(vol)

        resultText.text = "Add $dilString of the sample to $volString of DI or blank solution."
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