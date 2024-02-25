package com.example.mybarcode





import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class ScanCodeActivity : AppCompatActivity() {
    private lateinit var scanButton: Button
    private lateinit var backButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)


        backButton = findViewById(R.id.returnButton)
        scanButton = findViewById(R.id.scanNewCodeButton)



        backButton.setOnClickListener {
            finish()
        }
    }
}