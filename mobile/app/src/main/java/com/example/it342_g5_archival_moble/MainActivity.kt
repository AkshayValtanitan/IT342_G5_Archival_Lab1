package com.example.it342_g5_archival_mobile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Redirect straight to Login
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}