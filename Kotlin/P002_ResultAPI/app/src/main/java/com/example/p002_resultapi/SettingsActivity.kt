package com.example.p002_resultapi


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    private val TAG = "SettingsActivity:"
    // Регистрация Callback для контракта StartActivityForResult - получение ссылки на объект-лаунчер
    private var iARL = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val name = result.data!!.getStringExtra("name")
            (findViewById<View>(R.id.lblName) as TextView).text = name
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Обработчик события по нажатию, для перехода на другую Activity
        findViewById<View>(R.id.btnEditName).setOnClickListener { view: View? ->
            val intent = Intent(this, EditNameActivity::class.java)
            intent.putExtra(
                "currentName",
                (findViewById<View>(R.id.lblName) as TextView).text.toString()
            )
            iARL.launch(intent)
        }
        Log.d(TAG, "onCreate")
    }

    // Для сохранения значений при удалении Activity из памяти (нехватка памяти/смена ориентации экрана)
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

    // Отслеживание жизненного цикла Активности
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