package com.olq.timemanager.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.olq.timemanager.R
import com.olq.timemanager.services.TimerService
import kotlinx.android.synthetic.main.activity_manager.*
import org.jetbrains.anko.alert

class ManagerActivity : AppCompatActivity() {

    private val TAG = ManagerActivity::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        if (!TimerService.isRunning) {
            TimerService.taskName = intent.getStringExtra("TASK_TEXT")

            startService(Intent(this, TimerService::class.java))
        }

        myToggle.setOnClickListener { onBtnToggleClick() }
        myTaskText.text = TimerService.taskName
    }

    fun onBtnToggleClick(){
        if (myToggle.isChecked){
            startService(Intent(this, TimerService::class.java))
        } else {
            stopService(Intent(this, TimerService::class.java))
        }
    }



    fun onBtnExitClick(view: View){
        alert ("Do you want to exit?") {
            title = "Exit"
            positiveButton("Yes"){
                stopService(Intent(applicationContext, TimerService::class.java))
                TimerService.resetData()
                super.onBackPressed() }
            negativeButton("No"){}
        }.show()
    }



    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    public override fun onResume() {
        super.onResume()
        registerReceiver(br, IntentFilter(TimerService.SERVICE_ID))
        Log.d(TAG, "Registered BroadcastReceiver")
    }

    public override fun onPause() {
        super.onPause()
        unregisterReceiver(br)
        Log.d(TAG, "Unregistered BroadcastReceiver")
    }

    public override fun onStop() {
        try {
            unregisterReceiver(br)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onStop()
    }

    public override fun onDestroy() {
        super.onDestroy()
    }




    private val br = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateGUI(intent)
        }
    }

    private fun updateGUI(intent: Intent) {
        if (intent.extras != null) {
            val currentTime = intent.getLongExtra(TimerService.CURRENT_TIME_ID, 0)

            var seconds: Int = (currentTime / 1000).toInt()
            val minutes: Int = seconds / 60
            val hours: Int = minutes / 60

            seconds = seconds % 60

            Log.d(TAG, "countTime - h: $hours, min: $minutes, s: $seconds")
            myTimerText.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}