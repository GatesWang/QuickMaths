package com.wang.gates.quickmaths.classes

import android.content.Context
import android.media.MediaPlayer

class MusicPlayer(var context : Context) {

    companion object{
        var mp : MediaPlayer? = null
    }

    fun playSong(id : Int) {
        mp=MediaPlayer.create(context , id);
        mp!!.start();
        mp!!.isLooping = true
    }

}