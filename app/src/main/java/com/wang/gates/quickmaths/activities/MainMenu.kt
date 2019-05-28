package com.wang.gates.quickmaths.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.wang.gates.quickmaths.classes.GameSettings
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.activities.modes.GameLocalPlayer
import com.wang.gates.quickmaths.activities.modes.GameMultiplayer
import com.wang.gates.quickmaths.activities.modes.GameSinglePlayer
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
        single_player.setOnClickListener{
            gameSettings.setMode(GameSettings.SINGLE_PLAYER)
            startActivity(Intent(this@MainMenu, GameSinglePlayer::class.java))
        }
        multiplayer.setOnClickListener{
            gameSettings.setMode(GameSettings.MUlTIPLAYER)
            startActivity(Intent(this@MainMenu, GameMultiplayer::class.java))
        }
        local_player.setOnClickListener{
            gameSettings.setMode(GameSettings.LOCAL_PLAYER)
            startActivity(Intent(this@MainMenu, GameLocalPlayer::class.java))
        }
        settings.setOnClickListener{
            startActivity(Intent(this@MainMenu, Settings::class.java))
        }
    }
}
