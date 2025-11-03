package com.mybarcode




import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var createCodeButton: Button
    private lateinit var scanCodeButton:   Button
    private lateinit var exportedCodes:    Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createCodeButton = findViewById(R.id.createCodeButton)
        scanCodeButton = findViewById(R.id.scanCodeButton)
        exportedCodes = findViewById(R.id.exportedCodes)



        val createIntent = Intent(this, CreateCodeActivity::class.java)
        val scanIntent = Intent(this, ScanCodeActivity::class.java)
        val exportedIntent = Intent(this, SavedCodesActivity::class.java)



        createCodeButton.setOnClickListener {
            startActivity(createIntent)
        }


        scanCodeButton.setOnClickListener {
            startActivity(scanIntent)
        }


        exportedCodes.setOnClickListener {
            startActivity(exportedIntent)
        }
    }
}