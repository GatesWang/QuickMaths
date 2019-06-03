package com.wang.gates.quickmaths.classes

import android.content.Context
import android.media.MediaPlayer

class MusicPlayer() {

    companion object{
        var mp : MediaPlayer? = null
    }

    fun playSong(context: Context, id : Int) {
        mp = MediaPlayer.create(context , id);
        mp!!.start();
        mp!!.isLooping = true
    }

    fun stopPlaying(){
        mp!!.stop()
        mp!!.release()
    }

}