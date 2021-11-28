package com.example.p001_activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView


class AccountActivity : AppCompatActivity() {
    var btnExit: Button? = null
    val TAG = "AccountActivity:"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        Log.d(TAG, "onCreate")

        // Обработчик события нажатия на кнопку, завершение текущего Activity
        btnExit = findViewById<View>(R.id.btnExit) as Button
        val oclBtnExit = View.OnClickListener { finish() }
        btnExit!!.setOnClickListener(oclBtnExit)

        // Получение переданных данных по ключу "login" из интента
        val intent_n = intent
        val login_str = intent_n.getStringExtra("login")
        (findViewById<View>(R.id.lblHello) as TextView).text = "Привет, $login_str"

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }
}