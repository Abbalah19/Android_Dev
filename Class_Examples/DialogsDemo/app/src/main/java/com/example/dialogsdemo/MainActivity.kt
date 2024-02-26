package com.example.dialogsdemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /* Creates a basic alertdialog
    *  with a message and title, no button
   */
    fun showDialogVersion1(view: View) {
        // Create an alertdialog builder object,
        // then set attributes that you want the dialog to have
        val builder = AlertDialog.Builder(this)
        builder.setTitle("My Dialog")
        builder.setMessage("Welcome to my app!")

        // create the dialog and show it
        val dialog = builder.create()
        dialog.show()
    }

    /* Creates an alertdialog
    *  with a message, title, icon and actions buttons
    */
    fun showDialogVersion2(view: View) {

        // Create an alertdialog builder object,
        // then set attributes that you want the dialog to have
        val builder = AlertDialog.Builder(this)
        builder.setTitle("My Dialog")
        builder.setMessage("Are you sure that you want to delete this file?")
        // Set an icon, optional
        builder.setIcon(android.R.drawable.ic_delete)

        // Set the button actions, all of them are optional
        builder.setPositiveButton("YES"){ dialog, which ->
            // code to run when YES is pressed
        }

        builder.setNegativeButton("NO"){ dialog, which ->
            // code to run when NO is pressed
        }

        builder.setNeutralButton("Cancel"){ dialog, which ->
            // code to run when Cancel is pressed
        }

        // create the dialog and show it
        val dialog = builder.create()
        dialog.show()
    }

    /* Creates an alertdialog for single-choice items (radio buttons),
    *  with a message, title, icon and actions buttons
    */
    fun showDialogVersion3(view: View) {

        // Create an alertdialog builder object,
        // then set attributes that you want the dialog to have
        val builder = AlertDialog.Builder(this)
        builder.setIcon(android.R.drawable.ic_menu_help)
        builder.setTitle("Choose a color")

        // Set the button actions, optional
        builder.setPositiveButton("OK"){ dialog, which ->
            // code to run when OK is pressed
        }
        builder.setNeutralButton("Cancel"){ dialog, which ->
            // code to run when Cancel is pressed
        }


        // A list of colors to be shown
        val colors = arrayOf("Blue", "Red", "Yellow", "Green")

        // Specify the list array, the items to be selected by default (null for none),
        // and the listener through which to receive callbacks when items are selected
        builder.setSingleChoiceItems(colors, -1) { dialog, which ->
            Log.d(TAG, "Selected: ${colors[which]}")
            Toast.makeText(this, "${colors[which]} is selected", Toast.LENGTH_SHORT).show()
        }

        // The following can be used to show list of items
        //builder.setItems(colors){ _, which ->
        //   Log.d(TAG, "Selected: ${colors[which]}")
        //}

        // create the dialog and show it
        val dialog = builder.create()
        dialog.show()
    }
}