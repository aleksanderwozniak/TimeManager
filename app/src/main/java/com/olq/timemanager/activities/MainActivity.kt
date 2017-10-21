package com.olq.timemanager.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.olq.timemanager.R
import com.olq.timemanager.services.TimerService
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(TimerService.isRunning){
            startActivity<ManagerActivity>()
        } else {
            setContentView(R.layout.activity_main)
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }


    fun onAddBtnClick(view: View){
        if(myEditText.text.isEmpty()){
            onEditTextEmpty()
        } else {
            launchManagerActivity(myEditText.text.toString())
        }
    }

    private fun onEditTextEmpty(){
        toast("Task name can't be empty")
    }

    private fun launchManagerActivity(text: String){
        startActivity<ManagerActivity>("TASK_TEXT" to text)
    }
}
