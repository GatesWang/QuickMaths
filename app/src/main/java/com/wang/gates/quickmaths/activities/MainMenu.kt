package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import com.wang.gates.quickmaths.classes.Settings
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.MusicPlayer

import kotlinx.android.synthetic.main.main_menu.*

class MainMenu : AppCompatActivity() {
    private val gameSettings = Settings.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        setListeners()

        val musicPlayer = MusicPlayer()
        musicPlayer.playSong(this@MainMenu, R.raw.mansnothot)
    }

    private fun setListeners(){
        timer_mode.setOnClickListener{
            gameSettings.setMode(Settings.TIMER_MODE)
            startActivity(Intent(this@MainMenu, GameStart::class.java))
        }
        bomb_mode.setOnClickListener{
            gameSettings.setMode(Settings.BOMB_MODE)
            startActivity(Intent(this@MainMenu, GameStart::class.java))
        }
        challenge_friend.setOnClickListener{
            gameSettings.setMode(Settings.CHALLENGE_FRIEND_MODE)
            startActivity(Intent(this@MainMenu, GameStart::class.java))
        }
        settings.setOnClickListener{
            startActivity(Intent(this@MainMenu, GameSettings::class.java))
        }
    }
}
