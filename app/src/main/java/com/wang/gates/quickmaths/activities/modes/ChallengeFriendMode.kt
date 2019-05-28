package com.wang.gates.quickmaths.activities.modes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.wang.gates.quickmaths.R

class ChallengeFriendMode : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mode_sprint)

        ContextCompat.getColor(this@ChallengeFriendMode, R.color.colorPrimary)
    }
}
