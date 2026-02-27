package com.example.it342_g5_archival_mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.it342_g5_archival_mobile.databinding.ActivityRegisterBinding
import com.example.it342_g5_archival_mobile.network.AuthRequest
import com.example.it342_g5_archival_mobile.network.RetrofitClient
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirm = binding.etConfirmPassword.text.toString()

            when {
                username.isEmpty() || password.isEmpty() || confirm.isEmpty() ->
                    showError("All fields are required")
                password.length < 6 ->
                    showError("Password must be at least 6 characters")
                password != confirm ->
                    showError("Passwords do not match")
                else -> register(username, password)
            }
        }

        binding.btnGoToLogin.setOnClickListener {
            finish() // go back to LoginActivity
        }
    }

    private fun register(username: String, password: String) {
        setLoading(true)
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.register(AuthRequest(username, password))
                if (response.isSuccessful && response.body()?.success == true) {
                    // Registration done — go back to login
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                } else {
                    showError(response.body()?.message ?: "Registration failed")
                }
            } catch (e: Exception) {
                showError("Cannot connect to server. Is it running?")
            } finally {
                setLoading(false)
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnRegister.isEnabled = !loading
    }

    private fun showError(msg: String) {
        binding.tvError.text = msg
        binding.tvError.visibility = View.VISIBLE
    }
}