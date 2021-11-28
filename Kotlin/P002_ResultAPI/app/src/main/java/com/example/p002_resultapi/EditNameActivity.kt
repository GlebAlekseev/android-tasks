package com.example.p002_resultapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class EditNameActivity : AppCompatActivity() {
    private val TAG = "EditNameActivity:"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)

        // Заполнение поля значением из порождающей активности
        val intent_ = intent
        (findViewById<View>(R.id.name_inp) as EditText).setText(intent_.getStringExtra("currentName"))

        // Обработчик события для клика
        findViewById<View>(R.id.btnSaveName).setOnClickListener { view: View? ->
            val intent = Intent()
            intent.putExtra(
                "name",
                (findViewById<View>(R.id.name_inp) as EditText).text.toString()
            )
            // Установление возвращаемого интента для текущей активности, а также код завершения
            setResult(RESULT_OK, intent)
            // Завершение активности
            finish()
        }
        Log.d(TAG, "onCreate")
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