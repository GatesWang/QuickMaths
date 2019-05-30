package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.wang.gates.quickmaths.classes.GameSettings
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.MusicPlayer

import kotlinx.android.synthetic.main.main_menu.*

class MainMenu : AppCompatActivity() {
    private val gameSettings = GameSettings.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        setListeners()

        val musicPlayer = MusicPlayer(this@MainMenu)
        musicPlayer.playSong(R.raw.mansnothot)
    }

    private fun setListeners(){
        timer_mode.setOnClickListener{
            gameSettings.setMode(GameSettings.TIMER_MODE)
            startActivity(Intent(this@MainMenu, GameStart::class.java))
        }
        bomb_mode.setOnClickListener{
            gameSettings.setMode(GameSettings.BOMB_MODE)
            startActivity(Intent(this@MainMenu, GameStart::class.java))
        }
        challenge_friend.setOnClickListener{
            TODO("play with friends on facebook")
        }
        settings.setOnClickListener{
            startActivity(Intent(this@MainMenu, Settings::class.java))
        }
    }
}
