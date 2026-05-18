package com.example.nammavastra

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nammavastra.databinding.ActivityMainBinding
import com.example.nammavastra.ui.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Show welcome message with user name (if logged in)
        val sharedPreferences = getSharedPreferences("NammaVastraPrefs", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "User")
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            Toast.makeText(this, "Welcome $userName! 👋", Toast.LENGTH_LONG).show()
        }

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        supportActionBar?.title = "Namma-Vastra"
    }

    // Handle back button press - show logout dialog
    override fun onBackPressed() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Clear login session
                val sharedPreferences = getSharedPreferences("NammaVastraPrefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().clear().apply()

                // Navigate to Login screen
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No") { _, _ ->
                // ✅ FIXED: Call super.onBackPressed() when user cancels
                super.onBackPressed()
            }
            .show()
    }
}