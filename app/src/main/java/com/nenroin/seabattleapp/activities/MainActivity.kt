package com.nenroin.seabattleapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nenroin.seabattleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            startConnectMenuActivity()
        }
    }

    private fun startConnectMenuActivity() {
        val intent = Intent(this, ConnectMenuActivity::class.java)
        startActivity(intent)
    }
}