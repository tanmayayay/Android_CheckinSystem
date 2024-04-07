package com.example.trialapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trialapp.ui.Remote.ApiService
import com.example.trialapp.ui.Remote.Login
import com.example.trialapp.ui.Remote.Network
import com.example.trialapp.ui.Remote.User
import com.example.trialapp.ui.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerTextView = findViewById(R.id.textViewRegister)
        forgotPasswordTextView = findViewById(R.id.textViewForgotPassword)

        sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)

        // Set click listeners
        loginButton.setOnClickListener { login() }
        registerTextView.setOnClickListener { goToRegisterActivity() }
        forgotPasswordTextView.setOnClickListener { goToForgotPasswordActivity() }
    }

    private fun login() {
        val enteredUsername = usernameEditText.text.toString()
        val enteredPassword = passwordEditText.text.toString()

        // Prepare the Login object with the entered credentials
        val login = Login()
        login.setUsername(enteredUsername)
        login.setPassword(enteredPassword)

        // Create a Retrofit instance and call the API
        val apiService: ApiService = Network.getInstance().create(ApiService::class.java)
        val call: Call<User> = apiService.loginUser(login)
        call.enqueue(object : Callback<User?> {
            override fun onResponse(call: Call<User?>?, response: Response<User?>) {
                if (response.isSuccessful && response.body() != null) {
                    val user = response.body()

                    val sessionManager = SessionManager(applicationContext)
                    sessionManager.setLogin(true)
                    sessionManager.userName = user?.userName ?: ""

                    // Assuming User class has getMilitaryRank method along with getGivenName() and getLastName()
                    val givenName = user?.givenName ?: ""
                    val lastName = user?.lastName ?: ""
                    val militaryRank = user?.militaryRank ?: ""  // Retrieve militaryRank

                    // Store username, given name, last name, and military rank in SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("username", user?.userName)
                    editor.putString("givenName", givenName)
                    editor.putString("lastName", lastName)
                    editor.putString("militaryRank", militaryRank)  // Store military rank
                    editor.apply()

                    // Login successful, navigate to the MainActivity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Login failed
                    Toast.makeText(
                        this@LoginActivity,
                        "Login failed. Please check your credentials.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



            override fun onFailure(call: Call<User?>?, t: Throwable) {
                // API call failed
                Toast.makeText(this@LoginActivity, "Login failed: " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


//    private fun login() {
//        val enteredUsername = usernameEditText.text.toString()
//        val enteredPassword = passwordEditText.text.toString()
//
//        // Check if the user exists in SharedPreferences
//        if (sharedPreferences.contains(enteredUsername)) {
//            val userDetails = JSONObject(sharedPreferences.getString(enteredUsername, "")!!)
//            val storedPassword = userDetails.getString("password")
//
//            if (enteredPassword == storedPassword || (enteredUsername == "admin" && enteredPassword == "pass")) {
//                // Store the loggedInUser in SharedPreferences
//                val editor = sharedPreferences.edit()
//                editor.putString("loggedInUser", enteredUsername)
//                editor.apply()
//
//                // Log in successful, navigate to the main activity or your app's dashboard
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()  // Close the LoginActivity after successful login
//            }
//            else {
//                Toast.makeText(this, "Invalid password.", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(this, "Username not found.", Toast.LENGTH_SHORT).show()
//        }
//    }

    private fun goToRegisterActivity() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun goToForgotPasswordActivity() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }
}
