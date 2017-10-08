package com.olq.timemanager

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myAddButton.setOnClickListener{ onAddBtnClick() }
    }


    fun onAddBtnClick(){
        if(myEditText.text.isEmpty()){
            onEditTextEmpty()
        }else{
            launchManagerActivity(myEditText.text.toString())
        }
    }

    fun onEditTextEmpty(){
        toast("Task name can't be empty")
    }

    fun launchManagerActivity(text: String){
        startActivity<ManagerActivity>("TASK_TEXT" to text)
    }
}
