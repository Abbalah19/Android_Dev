// add delivery charge, code reset button, try to condense code

package com.example.homework_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.Spinner


var pizzaType = 0.00f;    var pizzaSize = 0.00f;    var toppingsPrice = 0.00f;
var delivery = 0.00f;    var addSpice = 0.00f;    var quantityMultiplier = 1;
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Spice level stuff
        // set val for switch, seekBar, and the textbox
        val spicySwitch = findViewById<Switch>(R.id.spicy_switch)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val spiceLevel = findViewById<TextView>(R.id.spice_number_textview)
        val spiceTextLabel = findViewById<TextView>(R.id.spicy_level_slide_textview)

        // Initially hide the SeekBar, its already invisible in the xml code??
        seekBar.visibility = View.GONE
        spiceLevel.visibility = View.GONE
        spiceTextLabel.visibility = View.GONE

        // Create object to attach listener to. Switch = type mismatch? otherwise same as in class example
        spicySwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (spicySwitch.isChecked) {
                    addSpice = 1.00f
                    spicySwitch.text = "Spicy"
                    seekBar.visibility = View.VISIBLE
                    spiceLevel.visibility = View.VISIBLE
                    spiceTextLabel.visibility = View.VISIBLE
                    totalCalc()
                } else {
                    addSpice = 0.00f
                    spicySwitch.text = "No spice"
                    spiceLevel.text = "0"
                    seekBar.progress = 0
                    seekBar.visibility = View.GONE
                    spiceLevel.visibility = View.GONE
                    spiceTextLabel.visibility = View.GONE
                    totalCalc()
                }
            }
        })

        // Direct from class example
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                spiceLevel.text = "$progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {  }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {  }
        })
        val deliverySwitch = findViewById<Switch>(R.id.delivery_switch)
        deliverySwitch.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (deliverySwitch.isChecked) {
                    delivery = 2.00f
                    deliverySwitch.text = "Yes ($2.00)"
                    totalCalc()
                } else {
                    delivery = 0.00f
                    deliverySwitch.text = "No ($0.00)"
                    totalCalc()
                }
            }
        })

        // Create a list of some strings that will be shown in the spinner
        val pizzaSizeOptions = listOf("Choose a Size", "Medium ($9.99)", "Large ($13.99)", "Extra Large ($15.99)", "Party Size ($25.99)")

        // Create an adapter with 3 parameters: activity (this), layout (using a pre-built layout), list to show
        val pizzaSizeAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pizzaSizeOptions)

        // Store the the listView widget in a variable
        val pizzaSpinner = findViewById<Spinner>(R.id.size_spinner)

        // set the adapter to the spinner
        pizzaSpinner.adapter = pizzaSizeAdapter

        // set the onItemSelectedListener as (this).  (this) refers to this activity that implements OnItemSelectedListener interface
        pizzaSpinner.onItemSelectedListener = this
    }

    // The following two methods are callback methods of OnItemSelectedListener interface
    override fun onNothingSelected(parent: AdapterView<*>?) {
        //Callback method to be invoked when the selection disappears from this view. For example,
        // if the list becomes empty and the ArrayAdapter is notified, this callback will be invoked
        Toast.makeText(this, "Nothing is selected!", Toast.LENGTH_SHORT).show()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // you can determine which item in the dropdown list is selected
        val selectedItem = parent?.getItemAtPosition(position) as String
        pizzaSize = when (selectedItem) {
            "Medium ($9.99)" -> 9.99f
            "Large ($13.99)" -> 13.99f
            "Extra Large ($15.99)" -> 15.99f
            "Party Size ($25.99)" -> 25.99f
            else -> 0.00f
        }
        totalCalc()
    }



    fun radioButtonClick(view:View){
        val pizzaImage = when (view.id) {
            R.id.pepperoni_radio -> R.drawable.pepperoni
            R.id.BBQ_radio -> R.drawable.bbq_chicken
            R.id.Margherita_radio -> R.drawable.margheritta
            R.id.hawaiian_radio -> R.drawable.hawaiian
            else -> R.drawable.pizza_crust
        }

        val pizzaTypePrice = when (view.id) {
            R.id.pepperoni_radio -> 1.99f
            R.id.BBQ_radio -> 1.99f
            R.id.Margherita_radio -> 4.99f
            R.id.hawaiian_radio -> 4.99f
            else -> 0.00f
        }
        findViewById<ImageView>(R.id.pizza_image).setImageResource(pizzaImage)
        pizzaType = pizzaTypePrice
        totalCalc()
    }

    fun topingsCheck(view: View){
        if (view is CheckBox){
            val checked: Boolean = view.isChecked

            when (view.id) {
                R.id.topping_tomato_checkbox -> {
                    if (checked) {
                        toppingsPrice += 1.00f
                    }
                    else{
                        toppingsPrice -= 1.00f
                    }
                }
                R.id.topping_mushrooms_checkboxcheckBox2 -> {
                    if (checked) {
                        toppingsPrice += 2.30f
                    }
                    else{
                        toppingsPrice -= 2.30f
                    }
                }
                R.id.topping_olives_checkbox -> {
                    if (checked) {
                        toppingsPrice += 1.70f
                    }
                    else{
                        toppingsPrice -= 1.70f
                    }
                }
                R.id.topping_onions_checkbox -> {
                    if (checked) {
                        toppingsPrice += 1.25f
                    }
                    else{
                        toppingsPrice -= 1.25f
                    }
                }
                R.id.topping_broccoli_checkbox-> {
                    if (checked) {
                        toppingsPrice += 1.80f
                    }
                    else{
                        toppingsPrice -= 1.80f
                    }
                }
                R.id.topping_spinach_checkbox-> {
                    if (checked) {
                        toppingsPrice += 2.00f
                    }
                    else{
                        toppingsPrice -= 2.00f
                    }
                }
            }
        }
        totalCalc()
    }

    fun quantMult(view: View){

        when (view.id){
            R.id.add_quant_button -> {
                quantityMultiplier +=1
                findViewById<TextView>(R.id.pizza_count_textview).text = "$quantityMultiplier"
            }
            R.id.sub_quant_button -> {
                if (quantityMultiplier > 1){
                    quantityMultiplier -= 1
                    findViewById<TextView>(R.id.pizza_count_textview).text = "$quantityMultiplier"
                }
                else {
                    Toast.makeText(this, "Can't have less than one pizza", Toast.LENGTH_SHORT).show()}
            }
        }
        totalCalc()
    }

    private fun totalCalc(){
        val subtotal = quantityMultiplier * (pizzaType + pizzaSize + toppingsPrice + delivery + addSpice )
        val formatSubTotal = String.format("%.2f", subtotal)
        findViewById<TextView>(R.id.sub_float_textview).text = "$formatSubTotal"

        var taxResult = 0.00f
        if (delivery == 2.00f && quantityMultiplier > 0){
            taxResult = (subtotal - (delivery* quantityMultiplier)) * 0.0635f
        }
        else {
            taxResult = (subtotal) * 0.0635f
        }
        val formatTax = String.format("%.2f", taxResult)
        findViewById<TextView>(R.id.tax_float_textview).text = "$formatTax"


        val total = (subtotal + taxResult)
        val formatTotal = String.format("%.2f", total)
        findViewById<TextView>(R.id.total_float_textview).text = "$formatTotal"
    }

    fun resetButton(view:View){
        pizzaType = 0.00f; pizzaSize = 0.00f; toppingsPrice = 0.00f;
        delivery = 0.00f; addSpice = 0.00f; quantityMultiplier = 1;
        findViewById<RadioGroup>(R.id.radioGroup).clearCheck()
        findViewById<ImageView>(R.id.pizza_image).setImageResource(R.drawable.pizza_crust)
        findViewById<Spinner>(R.id.size_spinner).setSelection(0)
        findViewById<CheckBox>(R.id.topping_tomato_checkbox).isChecked = false
        findViewById<CheckBox>(R.id.topping_mushrooms_checkboxcheckBox2).isChecked = false
        findViewById<CheckBox>(R.id.topping_olives_checkbox).isChecked = false
        findViewById<CheckBox>(R.id.topping_onions_checkbox).isChecked = false
        findViewById<CheckBox>(R.id.topping_broccoli_checkbox).isChecked = false
        findViewById<CheckBox>(R.id.topping_spinach_checkbox).isChecked = false
        findViewById<SeekBar>(R.id.seekBar).visibility = View.GONE
        findViewById<TextView>(R.id.pizza_count_textview).text = "1"

        val spicySwitch = findViewById<Switch>(R.id.spicy_switch)
        spicySwitch.isChecked = false

        val deliverySwitch = findViewById<Switch>(R.id.delivery_switch)
        deliverySwitch.isChecked = false
    }
}