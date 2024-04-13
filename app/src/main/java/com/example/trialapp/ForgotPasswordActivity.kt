package com.example.trialapp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.trialapp.ui.Remote.ApiService
import com.example.trialapp.ui.Remote.ForgotPasswordRequest
import com.example.trialapp.ui.Remote.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
//        usernameEditText = findViewById(R.id.editTextUsernameForgot)
//        securityQuestionEditText = findViewById(R.id.editTextSecurityQuestion)
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
//
//
//class ForgotPasswordActivity : AppCompatActivity() {
//
//    private lateinit var emailEditText: EditText
//    private lateinit var otpEditText: EditText
//    private lateinit var newPasswordEditText: EditText
//    private lateinit var confirmNewPasswordEditText: EditText
//    private lateinit var sendOtpButton: Button
//    private lateinit var updatePasswordButton: Button
//    private lateinit var backButton: Button
//
//    private var generatedOtp = "" // This will store the OTP sent to the user.
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_forgot_password)
//
//        // Initialize views
//        emailEditText = findViewById(R.id.editTextEmail)
//        otpEditText = findViewById(R.id.editTextOtp)
//        newPasswordEditText = findViewById(R.id.editTextNewPassword)
//        confirmNewPasswordEditText = findViewById(R.id.editTextConfirmNewPassword)
//        sendOtpButton = findViewById(R.id.buttonSendOtp)
//        updatePasswordButton = findViewById(R.id.buttonUpdatePassword)
//        backButton = findViewById(R.id.buttonBackForgot)
//
//        sendOtpButton.setOnClickListener { sendOtp() }
//        updatePasswordButton.setOnClickListener { updatePassword() }
//        backButton.setOnClickListener { finish() }
//    }
//
////    private fun sendOtp() {
////        val email = emailEditText.text.toString()
////        if (email.isNotEmpty()) {
////            // Generate and display the OTP. Replace this logic with actual email sending in a real app.
////            generatedOtp = (100000..999999).random().toString()
////            Toast.makeText(this, "OTP sent: $generatedOtp", Toast.LENGTH_LONG).show() // For demonstration only
////        } else {
////            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
////        }
////    }
//
//    private fun sendOtp() {
//        val email = emailEditText.text.toString()
//        if (!email.isEmpty()) {
//            val apiService: ApiService = Network.getInstance().create(ApiService::class.java)
//            val request = ForgotPasswordRequest(email)
//            val call: Call<Void> = apiService.forgotPasswordRequest(request)
//            call.enqueue(object : Callback<Void?>() {
//                override fun onResponse(call: Call<Void?>?, response: Response<Void?>) {
//                    if (response.isSuccessful()) {
//                        Toast.makeText(
//                            this@ForgotPasswordActivity,
//                            "OTP sent to your email.",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    } else {
//                        Toast.makeText(
//                            this@ForgotPasswordActivity,
//                            "Failed to send OTP. Try again.",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Void?>?, t: Throwable) {
//                    Toast.makeText(
//                        this@ForgotPasswordActivity,
//                        "Error: " + t.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            })
//        } else {
//            Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//
//    private fun updatePassword() {
//        val email = emailEditText.text.toString()
//        val otp = otpEditText.text.toString()
//        val newPassword = newPasswordEditText.text.toString()
//        val confirmNewPassword = confirmNewPasswordEditText.text.toString()
//
//        if (otp == generatedOtp) {
//            if (newPassword == confirmNewPassword) {
//                saveNewPassword(email, newPassword)
//                Toast.makeText(this, "Password updated successfully.", Toast.LENGTH_SHORT).show()
//                finish()
//            } else {
//                Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
//            }
//        } else {
//            Toast.makeText(this, "Invalid OTP.", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun saveNewPassword(email: String, newPassword: String) {
//        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putString(email + "_password", newPassword)
//        editor.apply()
//    }
//}