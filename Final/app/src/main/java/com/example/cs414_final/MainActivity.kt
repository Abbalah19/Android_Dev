/*
TODO
Add a catch for empty fields on the logIn page
 */

package com.example.cs414_final

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

private const val TAG = "CreateNewAccount_Activity"

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
    }

    fun signInUser(view: View) {
        hideKeyboard()
        if (findViewById<EditText>(R.id.email_editTextText).text.toString().isEmpty()){
            makeAlerts("Empty Email", "Please provide your email address")
            return
        }
        else if (findViewById<EditText>(R.id.password_editTextPassword).text.toString().isEmpty()){
            makeAlerts("Empty Password", "Please provide your password")
            return
        }
        else {
            val email = findViewById<EditText>(R.id.email_editTextText).text.toString()
            val password = findViewById<EditText>(R.id.password_editTextPassword).text.toString()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val intent = Intent(this, TicketSearch::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        makeAlerts(
                            "Authentication failed",
                            "Please check your email and password and try again"
                        )
                    }
                }
        }
    }
    fun createNewAccount(view: View) {
        //Toast.makeText(this, "New Account", Toast.LENGTH_SHORT).show()
        hideKeyboard()
        val intent = Intent(this, CreateNewAccount::class.java)
        startActivity(intent)
    }

    public override fun onStart() {
        super.onStart()
        /*
        // Check if user is signed in (non-null) and sign them out, for testing. maybe change
        // to keep user signed in?
        val currentUser = auth.currentUser
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut()
        }
         */
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, TicketSearch::class.java)
            startActivity(intent)
        }
    }

    private fun makeAlerts(title:String, message:String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK"){ _, _ ->}
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}