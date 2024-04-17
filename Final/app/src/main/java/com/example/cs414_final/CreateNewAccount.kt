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
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "CreateNewAccount_Activity"

class CreateNewAccount : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var fireDB: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_account)

        auth = FirebaseAuth.getInstance()
        fireDB = FirebaseFirestore.getInstance()

        val username = findViewById<EditText>(R.id.userName_editTextText)
        val password = findViewById<EditText>(R.id.newPassword_editTextTextPassword)
        val confPassword = findViewById<EditText>(R.id.confPassword_editTextTextPassword)
        val email = findViewById<EditText>(R.id.newEmail_editTextTextEmailAddress)
        val createAccount = findViewById<View>(R.id.submitNewAccount_button)

        createAccount.setOnClickListener {
            hideKeyboard()
            checkForEmptyFields(username.text.toString(), password.text.toString(), confPassword.text.toString(), email.text.toString())
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut()
        }
    }

    private fun checkForEmptyFields( username: String, password: String, confPassword: String, email: String) {
        if (username.isEmpty()){
            makeAlerts("Username is empty", "Please enter a username")
            return
        }
        else if (password.isEmpty()){
            makeAlerts("Password is empty", "Please enter a password")
            return
        }
        else if (confPassword.isEmpty() || password != confPassword){
            makeAlerts("Passwords do not match", "Please enter the same password in both fields")
            return
        }
        else if (email.isEmpty()){
            makeAlerts("Email is empty", "Please enter an email")
            return
        }
        else if (!email.contains("@") || !email.contains(".")){
            makeAlerts("Invalid email", "Please enter a valid email")
            }
        else if (password.length < 8){
            makeAlerts("Password too short", "Password must be at least 8 characters long")
            return
        }
        else {
            createNewAccount(username, password, email)
        }
    }

    private fun createNewAccount( username: String, password: String, email: String){
        //Toast.makeText(this, "Create New Account Function Hit", Toast.LENGTH_SHORT).show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser

                    val userProfile = hashMapOf(
                        "username" to username,
                        "email" to email
                    )

                    // Add a new document with a generated ID
                    fireDB.collection("userProfiles")
                        .document(user?.uid.toString())
                        .set(userProfile)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "DocumentSnapshot added with ID: ${userProfile}")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error adding document", e)
                        }
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: Any?) {
        if (user != null) {
            finish()
            val intent = Intent(this, TicketSearch::class.java)
            startActivity(intent)
            //makeAlerts("Account created", "Account created successfully")
        }
        else {
            makeAlerts("Account not created", "Sorry, there was an error creating your account. Please try again later.")
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