package com.example.p002_startactivityforresult

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class EditNameActivity : AppCompatActivity() {
    var btnSaveName: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_name)
        val intent_ = intent
        (findViewById<View>(R.id.name_inp) as EditText).setText(intent_.getStringExtra("currentName"))
        btnSaveName = findViewById(R.id.btnSaveName)
        btnSaveName!!.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent()
            intent.putExtra(
                "name",
                (findViewById<View>(R.id.name_inp) as EditText).text.toString()
            )
            setResult(RESULT_OK, intent)
            finish()
        })
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

    companion object {
        private const val TAG = "EditNameActivity:"
    }
}