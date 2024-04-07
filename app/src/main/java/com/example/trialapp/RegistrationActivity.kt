package com.example.trialapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trialapp.ui.Remote.ApiService
import com.example.trialapp.ui.Remote.Network
import com.example.trialapp.ui.Remote.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//class RegistrationActivity : AppCompatActivity() {
//
//    private lateinit var givenNameEditText: EditText
//    private lateinit var lastNameEditText: EditText
//    private lateinit var militaryIdEditText: EditText
//    private lateinit var rankEditText: EditText
//    private lateinit var usernameEditText: EditText
//    private lateinit var passwordEditText: EditText
//    private lateinit var registerButton: Button
//    private lateinit var sharedPreferences: SharedPreferences
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registration)
//
//        // Initialize views
//        givenNameEditText = findViewById(R.id.editTextGivenName)
//        lastNameEditText = findViewById(R.id.editTextLastName)
//        militaryIdEditText = findViewById(R.id.editTextMilitaryID)
//        rankEditText = findViewById(R.id.editTextRank)
//        usernameEditText = findViewById(R.id.editTextUsernameRegister)
//        passwordEditText = findViewById(R.id.editTextPasswordRegister)
//        registerButton = findViewById(R.id.buttonRegister)
//
//        sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)
//
//        // Set click listener
//        registerButton.setOnClickListener { registerUser() }
//    }
//    private fun registerUser() {
//        val givenName = givenNameEditText.text.toString()
//        val lastName = lastNameEditText.text.toString()
//        val militaryId = militaryIdEditText.text.toString()
//        val rank = rankEditText.text.toString()
//        val username = usernameEditText.text.toString()
//        val password = passwordEditText.text.toString()
//
//        if (username.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Username or Password cannot be empty.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val user = User(givenName, lastName, militaryId, rank, username, password)
//        val apiService: ApiService = Network.getInstance().create(ApiService::class.java)
//        val call: Call<User> = apiService.registerUser(user) // Assume the API returns the User object upon successful registration
//
//        call.enqueue(object : Callback<User> {
//            override fun onResponse(call: Call<User>?, response: Response<User>) {
//                if (response.isSuccessful && response.body() != null) {
//                    // Registration successful, user details are returned
//                    Toast.makeText(
//                        this@RegistrationActivity,
//                        "Registered successfully!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    finish() // Return to the LoginActivity
//                } else {
//                    // Registration failed, no user details are returned
//                    Toast.makeText(
//                        this@RegistrationActivity,
//                        "Registration failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<User>?, t: Throwable) {
//                Toast.makeText(this@RegistrationActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

class RegistrationActivity : AppCompatActivity() {

    private lateinit var givenNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText // Added for the email
    private lateinit var militaryIdEditText: EditText
    private lateinit var rankEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize views
        givenNameEditText = findViewById(R.id.editTextGivenName)
        lastNameEditText = findViewById(R.id.editTextLastName)
        emailEditText = findViewById(R.id.editTextEmail) // Initialize the email EditText
        militaryIdEditText = findViewById(R.id.editTextMilitaryID)
        rankEditText = findViewById(R.id.editTextRank)
        usernameEditText = findViewById(R.id.editTextUsernameRegister)
        passwordEditText = findViewById(R.id.editTextPasswordRegister)
        registerButton = findViewById(R.id.buttonRegister)

        sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)

        // Set click listener
        registerButton.setOnClickListener { registerUser() }
    }

    private fun registerUser() {
        val givenName = givenNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val email = emailEditText.text.toString() // Get the email input
        val militaryId = militaryIdEditText.text.toString()
        val rank = rankEditText.text.toString()
        val username = usernameEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Check for empty fields (you might want to include more checks for the other fields)
        if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Username, password, or email cannot be empty.", Toast.LENGTH_SHORT).show()
            return
        }

        // Assuming User class has been updated to include the email field
        val user = User(givenName, lastName, email, militaryId, rank, username, password)
        val apiService: ApiService = Network.getInstance().create(ApiService::class.java)
        val call: Call<User> = apiService.registerUser(user)

                // Save user details in shared preferences
                val editor = sharedPreferences.edit()
                editor.putString("givenName", givenName)
                editor.putString("lastName", lastName)
                editor.putString("rank", rank)
                editor.apply()


        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>) {
                if (response.isSuccessful && response.body() != null) {
                    Toast.makeText(this@RegistrationActivity, "Registered successfully!", Toast.LENGTH_SHORT).show()
                    finish() // Return to the LoginActivity
                } else {
                    Toast.makeText(this@RegistrationActivity, "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable) {
                Toast.makeText(this@RegistrationActivity, "Error: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}

//
//    private fun registerUser() {
//        val givenName = givenNameEditText.text.toString()
//        val lastName = lastNameEditText.text.toString()
//        val militaryId = militaryIdEditText.text.toString()
//        val rank = rankEditText.text.toString()
//        val username = usernameEditText.text.toString()
//        val password = passwordEditText.text.toString()
//        if (username.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Username or Password cannot be empty.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        val user = User(givenName, lastName, militaryId, rank, username, password)
//        val apiService: ApiService = Network.getInstance().create(
//            ApiService::class.java)
//        val call: Call<Void> = apiService.registerUser(user)
//        call.enqueue(object : Callback<Void?> {
//            override fun onResponse(call: Call<Void?>?, response: Response<Void?>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(
//                        this@RegistrationActivity,
//                        "Registered successfully!",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    finish() // Return to the LoginActivity
//                } else {
//                    Toast.makeText(
//                        this@RegistrationActivity,
//                        "Registration failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<Void?>?, t: Throwable) {
//                Toast.makeText(this@RegistrationActivity, "Error: " + t.message, Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }

//    private fun registerUser() {
//        val givenName = givenNameEditText.text.toString()
//        val lastName = lastNameEditText.text.toString()
//        val militaryId = militaryIdEditText.text.toString()
//        val rank = rankEditText.text.toString()
//        val username = usernameEditText.text.toString()
//        val password = passwordEditText.text.toString()
//
//        if (username.isBlank() || password.isBlank()) {
//            Toast.makeText(this, "Username or Password cannot be empty.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        // Check if username already exists
//        if (sharedPreferences.contains(username)) {
//            Toast.makeText(this, "Username already exists. Choose a different one.", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val userDetails = JSONObject()
//        userDetails.put("password", password)
//        userDetails.put("givenName", givenName)
//        userDetails.put("lastName", lastName)
//        userDetails.put("militaryId", militaryId)
//        userDetails.put("rank", rank)
//
//        // Save the user details in SharedPreferences using the username as the key
//        val editor = sharedPreferences.edit()
//        editor.putString(username, userDetails.toString())
//        editor.apply()
//
//        Toast.makeText(this, "Registered successfully!", Toast.LENGTH_SHORT).show()
//        finish()  // Return to the LoginActivity
//    }
//}
