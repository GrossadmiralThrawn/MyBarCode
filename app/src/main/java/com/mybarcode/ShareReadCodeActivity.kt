package com.mybarcode

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream

class ShareReadCodeActivity : AppCompatActivity() {
    private lateinit var returnButton: Button
    private lateinit var shareAsQR:    Button
    private lateinit var shareAsBar:   Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_share_read_code)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val receivedText = intent.getStringExtra("EXTRA_TEXT")


        returnButton = findViewById(R.id.returnButtonShareAc)
        shareAsQR    = findViewById(R.id.shareAsQR)
        shareAsBar   = findViewById(R.id.shareAsBar)



        returnButton.setOnClickListener {
            finish()
        }
        shareAsQR.setOnClickListener {
            generateQRCode(receivedText.toString())
            finish()
        }
        shareAsBar.setOnClickListener {
            generateBarcode(receivedText.toString())
            finish()
        }
    }




    private fun generateQRCode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 600, 400)
            shareImage(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




    private fun generateBarcode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.CODE_128, 800, 400)
            shareImage(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }





    private fun shareImage(bitmap: Bitmap) {
        try {
            // Save bitmap to cache directory
            val cachePath = File(cacheDir, "images")
            cachePath.mkdirs() // Create directory if not exists
            val file = File(cachePath, "shared_image.png")
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()

            // Get file URI
            val fileUri: Uri = FileProvider.getUriForFile(
                this,
                "$packageName.fileprovider",
                file
            )

            // Create share intent
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, fileUri)
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant permission to read the URI
            }

            // Start share activity
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_via)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}