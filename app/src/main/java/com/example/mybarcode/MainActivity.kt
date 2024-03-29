package com.example.mybarcode




import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.PopupMenu
import com.example.mybarcode.importfolder.ImportLocal


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
        val exportingIntent = Intent(this, CodeExportingActivity::class.java)



        createCodeButton.setOnClickListener {
            startActivity(createIntent)
        }


        scanCodeButton.setOnClickListener {
            startActivity(scanIntent)
        }


        exportedCodes.setOnClickListener {
            startActivity(exportingIntent)
        }
    }
}