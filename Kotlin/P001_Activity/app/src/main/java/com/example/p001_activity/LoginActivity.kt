package com.example.p001_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat

class LoginActivity : AppCompatActivity() {

    private var btnGo: Button? = null
    private var loginInp: EditText? = null
    private val TAG = "LoginActivity:"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate")

        // Обработчик событие по нажатию, для перехода на другую Activity с сохранением значения "login"
        btnGo = findViewById<View>(R.id.btnAuth) as Button
        val intent = Intent(this, AccountActivity::class.java)
        val oclBtnGo = View.OnClickListener {
            intent.putExtra(
                "login",
                (findViewById<View>(R.id.login_inp) as EditText).text.toString()
            )
            startActivity(intent)
        }
        btnGo!!.setOnClickListener(oclBtnGo)

        // Обработчик события на изменение значения инпута, проверка на Enabled
        loginInp = findViewById<View>(R.id.login_inp) as EditText
        loginInp!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                checkInput()
            }
            override fun afterTextChanged(s: Editable) {}
        })
        // Проверка на Enabled
        checkInput()
    }

    private fun checkInput() {
        if (loginInp!!.length() != 0) {
            btnGo!!.isEnabled = true
            btnGo!!.setBackgroundColor(ContextCompat.getColor(this,R.color.color_1))
            btnGo!!.setTextColor(ContextCompat.getColor(this,R.color.color_2))
        } else {
            btnGo!!.isEnabled = false
            btnGo!!.setBackgroundColor(ContextCompat.getColor(this,R.color.disabledBG))
            btnGo!!.setTextColor(ContextCompat.getColor(this,R.color.disabledTextColor))
        }
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