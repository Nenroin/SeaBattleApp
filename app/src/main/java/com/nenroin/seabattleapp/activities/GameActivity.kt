package com.nenroin.seabattleapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nenroin.seabattleapp.R
import com.nenroin.seabattleapp.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}
