package com.olq.timemanager

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val startTime: Long = System.currentTimeMillis()

    fun onToggle(){
        if(myToggle.isChecked){
        }else{
        }
    }

    fun countTime(){
        val currentTime: Long = System.currentTimeMillis() - startTime
        var seconds: Int = (currentTime / 1000).toInt()
        val minutes: Int = seconds / 60
        val hours: Int = minutes / 60

        seconds = seconds % 60

        Log.d("     countTime", "h: $hours, min: $minutes, s: $seconds")

        myTimerText.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myToggle.setOnClickListener { onToggle() }

        val handler = Handler()
        val runnable = object : Runnable{
            override fun run(){
                countTime()
                handler.postDelayed(this, 1000)
            }
        }

        handler.post { runnable.run() }
    }
}
