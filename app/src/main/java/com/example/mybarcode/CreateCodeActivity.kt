package com.example.mybarcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CreateCodeActivity : AppCompatActivity() {
    private lateinit var returnButton: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_code)
        returnButton     = findViewById(R.id.returnFromCreateButton)


        returnButton.setOnClickListener {
            finish()
        }
    }
}