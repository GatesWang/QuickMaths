package com.wang.gates.quickmaths.activities.modes

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.wang.gates.quickmaths.R
import com.wang.gates.quickmaths.activities.Game
import com.wang.gates.quickmaths.classes.GameSettings
import kotlinx.android.synthetic.main.mode_timer.*

class BombMode : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var timeControl : Int = GameSettings.ONE_MINUTE
    private var difficulty : Int = GameSettings.EASY
    private val settings = GameSettings.getInstance()
    private var preferences : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mode_bomb)
        preferences = PreferenceManager.getDefaultSharedPreferences(this@BombMode)
        editor = preferences!!.edit()

        populateSpinners()
        setSpinnerBehavior()
        setButtonBehavior()
        setSpinnerSelected()
    }

    private fun populateSpinners(){
        ArrayAdapter.createFromResource(
            this,
            R.array.bomb_time_controls,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            time_spinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.difficulties,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            difficulty_spinner.adapter = adapter
        }
    }
    private fun setSpinnerBehavior(){
        difficulty_spinner.onItemSelectedListener = this
        time_spinner.onItemSelectedListener = this
    }
    private fun setSpinnerSelected(){
        if(settings.getMode()==GameSettings.BOMB_MODE) {
            val preferredDifficulty = preferences!!.getString(settings.getMode().toString() + "difficulty", null)
            val preferredTimeControl = preferences!!.getString(settings.getMode().toString() + "timecontrol", null)

            if (preferredDifficulty != null && preferredTimeControl != null) {
                difficulty_spinner.setSelection(preferredDifficulty.toInt())
                time_spinner.setSelection(preferredTimeControl.toInt()-3)
                //this is because bomb time controls start from 3
            }
        }
    }
    private fun setButtonBehavior(){
        start_timer_game.setOnClickListener{
            settings.setMode(GameSettings.BOMB_MODE)
            settings.setDifficulty(difficulty)
            settings.setTimeControl(timeControl)
            startActivity(Intent(this@BombMode, Game::class.java))
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val selected = parent.getItemAtPosition(pos)
        val timeControls = resources.getStringArray(R.array.bomb_time_controls)
        val difficulties = resources.getStringArray(R.array.difficulties)
        if(parent.getItemAtPosition(pos) is String){
            when(selected){
                //time controls
                timeControls[0] ->{
                    timeControl = GameSettings.FIVE_SECONDS
                }
                timeControls[1] ->{
                    timeControl = GameSettings.TEN_SECONDS
                }
                timeControls[2] ->{
                    timeControl = GameSettings.FIFTEEN_SECONDS
                }
                //difficulties
                difficulties[0] ->{
                    difficulty = GameSettings.EASY
                }
                difficulties[1] ->{
                    difficulty = GameSettings.MEDIUM
                }
                difficulties[2] ->{
                    difficulty = GameSettings.HARD
                }
            }
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>) {

    }
}
