package com.wang.gates.quickmaths.activities

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.classes.MusicPlayer
import kotlinx.android.synthetic.main.settings.*

class GameSettings : AppCompatActivity() {

    private var preferences : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null
    private var musicSetting : String = "On" //on by default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(this@GameSettings)
        editor = preferences!!.edit()
        setContentView(R.layout.settings)
        showMusicSettings()
        setOnChangeBehavior()
    }

    private fun showMusicSettings(){
        musicSetting = preferences!!.getString("music", "On")//on by default
        Log.d("music", musicSetting)
        if(musicSetting=="On"){
            music_switch.isChecked = true
            music_switch.text = "On"
        }
        else if(musicSetting=="Off"){
            music_switch.isChecked = false
            music_switch.text = "Off"
        }
    }

    private fun setOnChangeBehavior(){
        music_switch.setOnCheckedChangeListener { _, isChecked ->
            val mp = MusicPlayer()
            if (isChecked) {
                mp.playSong(this@GameSettings, R.raw.mansnothot)
                music_switch.text = "On"
                saveMusicSettings("On")

            } else {
                mp.stopPlaying()
                music_switch.text = "Off"
                saveMusicSettings("Off")
            }
        }
    }

    private fun saveMusicSettings(settings: String){
        editor!!.putString("music", "$settings")
        editor!!.commit()
    }


}
