package com.example.mybarcode





import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
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
        backButton = findViewById(R.id.returnButton)
        scanButton = findViewById(R.id.scanNewCodeButton)



        backButton.setOnClickListener {
            finish()
        }


        fun scanCode() {
            val scanOptions = ScanOptions()
            scanOptions.setPrompt("Volume up to flash on.")
            scanOptions.setOrientationLocked(true)
            scanOptions.setBeepEnabled(false)
            scanOptions.setCaptureActivity(CaptureAct::class.java)
            barLauncher.launch(scanOptions)
        }

        scanButton.setOnClickListener {
            scanCode()
            // AutoLink für Links aktivieren
            resultTextView.autoLinkMask = android.text.util.Linkify.WEB_URLS
            resultTextView.setOnClickListener { view ->
                // Überprüfen, ob das geklickte Text ein Link ist
                val textView = view as TextView
                val text = textView.text.toString()
                if (android.util.Patterns.WEB_URL.matcher(text).matches()) {
                    // Wenn ja, öffne den Link im Browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                    startActivity(intent)
                }
            }
        }
    }
}