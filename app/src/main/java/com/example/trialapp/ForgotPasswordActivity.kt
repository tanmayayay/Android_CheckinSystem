package com.example.trialapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var securityQuestionEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmNewPasswordEditText: EditText
    private lateinit var updatePasswordButton: Button
    private lateinit var backButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize views
        usernameEditText = findViewById(R.id.editTextUsernameForgot)
        securityQuestionEditText = findViewById(R.id.editTextSecurityQuestion)
        newPasswordEditText = findViewById(R.id.editTextNewPassword)
        confirmNewPasswordEditText = findViewById(R.id.editTextConfirmNewPassword)
        updatePasswordButton = findViewById(R.id.buttonUpdatePassword)
        backButton = findViewById(R.id.buttonBackForgot)

        updatePasswordButton.setOnClickListener { updatePassword() }
        backButton.setOnClickListener { finish() }
    }

    private fun updatePassword() {
        val username = usernameEditText.text.toString()
        val securityAnswer = securityQuestionEditText.text.toString()
        val newPassword = newPasswordEditText.text.toString()
        val confirmNewPassword = confirmNewPasswordEditText.text.toString()

        if (validateSecurityAnswer(username, securityAnswer)) {
            if (newPassword == confirmNewPassword) {
                saveNewPassword(username, newPassword)
                Toast.makeText(this, "Password updated successfully.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Invalid username or security answer.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateSecurityAnswer(username: String, answer: String): Boolean {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val storedAnswer = sharedPreferences.getString(username + "_securityAnswer", "")
        return answer == storedAnswer
    }

    private fun saveNewPassword(username: String, newPassword: String) {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(username + "_password", newPassword)
        editor.apply()
    }
}
