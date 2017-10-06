package com.olq.timemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var startTime: Long = System.currentTimeMillis()
    var currentTime: Long = 0
    var timeSavedState: Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myToggle.setOnClickListener { onBtnClick() }
    }



    fun onBtnClick(){
        timeSavedState = currentTime
        startTime = System.currentTimeMillis()

        val handler = Handler()
        val runnable = object : Runnable{
            override fun run(){
                updateTimer(handler, this)
            }
        }

        handler.post { runnable.run() }
    }


    fun updateTimer(handler: Handler, runnable: Runnable){
        if(myToggle.isChecked){
            countTime()
            handler.postDelayed(runnable, 1000)
        }
    }


    fun countTime(){
        currentTime = System.currentTimeMillis() - startTime + timeSavedState
        var seconds: Int = (currentTime / 1000).toInt()
        val minutes: Int = seconds / 60
        val hours: Int = minutes / 60

        seconds = seconds % 60

        Log.d("countTime", "h: $hours, min: $minutes, s: $seconds")
        myTimerText.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
