package com.example.luckywheel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val luckyWheel = findViewById<LuckyWheel>(R.id.lucky_wheel)
        findViewById<Button>(R.id.btn_play).setOnClickListener {
            luckyWheel.spinLuckyWheel()
        }
    }
}