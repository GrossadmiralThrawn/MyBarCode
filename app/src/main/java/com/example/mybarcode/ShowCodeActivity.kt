package com.example.mybarcode

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class ShowCodeActivity : AppCompatActivity() {
    private lateinit var returnButton: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        returnButton = findViewById(R.id.returnToPreviousButton)
    }



    private fun generateQRCode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400)
            // Hier kannst du das generierte Bild verwenden, z.B. in einer ImageView anzeigen
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




    private fun generateBarcode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.CODE_128, 400, 200)
            // Hier kannst du das generierte Bild verwenden, z.B. in einer ImageView anzeigen
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}