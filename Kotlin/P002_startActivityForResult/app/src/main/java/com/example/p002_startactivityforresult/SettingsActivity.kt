package com.example.p002_startactivityforresult

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    private val TAG = "SettingsActivity:"
    val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        // Обработчик событие по нажатию, для перехода на другую Activity
        findViewById<View>(R.id.btnEditName).setOnClickListener { view: View? ->
            val intent = Intent(this, EditNameActivity::class.java)
            intent.putExtra(
                "currentName",
                (findViewById<View>(R.id.lblName) as TextView).text.toString()
            )
            startActivityForResult(intent, REQUEST_CODE)
        }
        Log.d(TAG, "onCreate")
    }

    // Получение результата порожденного Activity, после закрытия
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE -> {
                val name = data!!.getStringExtra("name")
                (findViewById<View>(R.id.lblName) as TextView).text = name
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("sName", (findViewById<View>(R.id.lblName) as TextView).text.toString())
        Log.d(TAG, "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        (findViewById<View>(R.id.lblName) as TextView).text = savedInstanceState.getString("sName")
        Log.d(TAG, "onRestoreInstanceState")
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