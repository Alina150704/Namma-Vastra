package com.example.nammavastra.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nammavastra.R
import com.example.nammavastra.databinding.ActivitySignupBinding
import com.example.nammavastra.model.User
import com.google.firebase.firestore.FirebaseFirestore

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Back button
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Login text click
        binding.tvLogin.setOnClickListener {
            finish()
        }

        // Signup button click
        binding.btnSignup.setOnClickListener {
            val fullName = binding.etFullName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()

            if (validateInputs(fullName, email, phone, password, confirmPassword)) {
                createAccount(fullName, email, phone, password)
            }
        }
    }

    private fun validateInputs(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (fullName.isEmpty()) {
            binding.etFullName.error = "Full name is required"
            binding.etFullName.requestFocus()
            return false
        }
        if (email.isEmpty()) {
            binding.etEmail.error = "Email is required"
            binding.etEmail.requestFocus()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Invalid email format"
            binding.etEmail.requestFocus()
            return false
        }
        if (phone.isEmpty()) {
            binding.etPhone.error = "Phone number is required"
            binding.etPhone.requestFocus()
            return false
        }
        if (password.isEmpty()) {
            binding.etPassword.error = "Password is required"
            binding.etPassword.requestFocus()
            return false
        }
        if (password.length < 6) {
            binding.etPassword.error = "Password must be at least 6 characters"
            binding.etPassword.requestFocus()
            return false
        }
        if (password != confirmPassword) {
            binding.etConfirmPassword.error = "Passwords do not match"
            binding.etConfirmPassword.requestFocus()
            return false
        }
        return true
    }

    private fun createAccount(fullName: String, email: String, phone: String, password: String) {
        // Check if user already exists
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { result ->
                if (result.documents.isNotEmpty()) {
                    Toast.makeText(this, "User already exists! Please login.", Toast.LENGTH_LONG).show()
                } else {
                    // Create new user
                    val user = User(
                        fullName = fullName,
                        email = email,
                        phone = phone,
                        password = password
                    )

                    db.collection("users")
                        .add(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Account created successfully! Please login.", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Signup failed: ${it.message}", Toast.LENGTH_LONG).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Signup failed: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }
}