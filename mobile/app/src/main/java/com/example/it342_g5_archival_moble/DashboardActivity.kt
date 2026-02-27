package com.example.it342_g5_archival_mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.it342_g5_archival_mobile.databinding.ActivityDashboardBinding
import com.example.it342_g5_archival_mobile.network.RetrofitClient
import com.example.it342_g5_archival_mobile.network.SessionCookieJar
import kotlinx.coroutines.launch

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get username passed from LoginActivity
        val username = intent.getStringExtra("username") ?: "User"
        updateUI(username)

        // Also fetch fresh profile from backend
        fetchProfile()

        binding.btnLogout.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(username: String) {
        binding.tvHello.text = "Hello, $username!"
        binding.tvUsername.text = username
        binding.tvAvatar.text = username.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
    }

    private fun fetchProfile() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getMe()
                if (response.isSuccessful) {
                    val userData = response.body()?.data
                    if (userData != null) {
                        updateUI(userData.username)
                    }
                }
            } catch (_: Exception) {
                // silently fail — UI already populated from login
            }
        }
    }

    private fun logout() {
        lifecycleScope.launch {
            try {
                RetrofitClient.api.logout()
            } catch (_: Exception) {}
            SessionCookieJar.clear()
            val intent = Intent(this@DashboardActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}