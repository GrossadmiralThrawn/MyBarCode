package com.example.mybarcode





import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class ScanCodeActivity : AppCompatActivity() {
    private lateinit var resultString:   String
    private lateinit var scanButton:     Button
    private lateinit var backButton:     Button
    private lateinit var resultTextView: TextView
    private var          barLauncher              = registerForActivityResult(
        ScanContract()) { result: ScanIntentResult ->
        if (result.contents != null) {
            resultString = result.contents
            resultTextView.text = resultString
        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)


        resultTextView = findViewById(R.id.scannedResultTextView)
        backButton     = findViewById(R.id.returnButton)
        scanButton     = findViewById(R.id.scanNewCodeButton)



        backButton.setOnClickListener {
            finish()
        }

        scanButton.setOnClickListener {
            scanCode()
        }
    }


    private fun scanCode() {
        val scanOptions = ScanOptions()
        scanOptions.setPrompt("Volume up to flash on.")
        scanOptions.setOrientationLocked(true)
        scanOptions.setBeepEnabled(false)
        scanOptions.setCaptureActivity(CaptureAct::class.java)
        barLauncher.launch(scanOptions)
    }
}