package com.nenroin.seabattleapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nenroin.seabattleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlayOffline.setOnClickListener(){
            CreateOfflineGame()
        }
        binding.btnPlayOnline.setOnClickListener(){
            CreateOnlineGame()
        }
    }

    fun CreateOfflineGame(){

    }

    fun CreateOnlineGame(){

    }

    fun StartGame(){

    }
}