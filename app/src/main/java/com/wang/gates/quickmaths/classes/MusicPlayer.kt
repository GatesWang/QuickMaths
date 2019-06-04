package com.wang.gates.quickmaths.classes

import android.content.Context
import android.media.MediaPlayer
import android.preference.PreferenceManager

class MusicPlayer() {

    companion object{
        var mp : MediaPlayer? = null
    }

    fun musicOn(context: Context) : Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val musicSetting = preferences.getString("music", "On")//on by default
        return (musicSetting=="On")
    }

    fun playSong(context: Context, id : Int) {
        mp = MediaPlayer.create(context , id)
        mp!!.start()
        mp!!.isLooping = true
    }

    fun stopPlaying(){
        mp!!.stop()
        mp!!.release()
    }

}