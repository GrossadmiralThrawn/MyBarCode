package com.example.mybarcode

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class ShowCodeActivity : AppCompatActivity() {
    private lateinit var returnButton:      Button
    private lateinit var saveButton:        Button
    private lateinit var shareButton:       Button
    private lateinit var receivedIntent:    Intent
    private lateinit var showCodeImageView: ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_show_code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        receivedIntent    = intent
        returnButton      = findViewById(R.id.returnToPreviousButton)
        saveButton        = findViewById(R.id.saveButton)
        shareButton       = findViewById(R.id.shareButton)
        showCodeImageView = findViewById(R.id.showCodeImageView)
        val codeText      = receivedIntent.getStringExtra("codeText")



        when (receivedIntent.getStringExtra("codeType")) //Kotlins switch-case-Version
        {
            "QR" -> generateQRCode(codeText!!) //Lambdafunktionen, inklusive Fehlerabfrage, falls übergebene Daten == null
            "Bar" -> generateBarcode(codeText!!)
        }


        returnButton.setOnClickListener {
            finish()
        }
    }



    private fun generateQRCode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400)
            showCodeImageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




    private fun generateBarcode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.CODE_128, 400, 200)
            showCodeImageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}