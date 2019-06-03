package com.wang.gates.quickmaths.activities

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.MusicPlayer
import kotlinx.android.synthetic.main.settings.*

class GameSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        music_switch.setOnCheckedChangeListener { _, isChecked ->
            val mp = MusicPlayer()
            if (isChecked) {
                mp.playSong(this@GameSettings, R.raw.mansnothot)
                music_switch.text = "On"
            } else {
                mp.stopPlaying()
                music_switch.text = "Off"
            }
        }
    }
}
