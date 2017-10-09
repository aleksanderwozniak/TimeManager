package com.olq.timemanager.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.olq.timemanager.activities.ManagerActivity
import com.olq.timemanager.R

/**
 * Created by olq on 09.10.17.
 */

class TimerService : Service(){

    private val TAG = TimerService::class.java.simpleName
    private val CHANNEL_ID = "notification channel id"

    private val tsIntent = Intent(SERVICE_ID)
    private val handler = Handler()
    private var runnable: Runnable ?= null


    companion object {
        val SERVICE_ID = TimerService::class.java.`package`.name
        val CURRENT_TIME_ID = "current time id"

        var timeSavedState: Long = 0
        var startTime: Long = System.currentTimeMillis()
        var currentTime: Long = 0

        var isRunning = false
        var taskName: String = ""

        fun resetData(){
            timeSavedState = 0
            startTime = System.currentTimeMillis()
            currentTime = 0
        }
    }



    override fun onCreate() {
        super.onCreate()

        initTimer()
        isRunning = true
    }


    fun initTimer(){
        startTime = System.currentTimeMillis()

        runnable = object : Runnable{
            override fun run(){
                updateTimer(handler, this) }
        }

        handler.post { runnable!!.run() }
    }

    fun updateTimer(handler: Handler, runnable: Runnable){
        countTime()
        handler.postDelayed(runnable, 1000)
    }

    fun countTime(){
        currentTime = System.currentTimeMillis() - startTime + timeSavedState

        tsIntent.putExtra(CURRENT_TIME_ID, currentTime)
        sendBroadcast(tsIntent)
    }



    override fun onDestroy() {
        timeSavedState = currentTime

        if (runnable != null){
            handler.removeCallbacks(runnable)
        }

        isRunning = false
        super.onDestroy()
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "Received foreground intent ")

        val notificationIntent = Intent(this, ManagerActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Time Manager")
                .setTicker("Observing $taskName!")
                .setContentText(taskName)
                .setSmallIcon(R.drawable.ic_notif_timer)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build()

        startForeground(101,
                notification)

        return Service.START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}