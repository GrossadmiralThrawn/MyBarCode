package com.mybarcode




import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.mybarcode.exportfolder.ExportLocal
import com.mybarcode.exportfolder.IExport
import java.io.File
import java.io.FileOutputStream




class ShowCodeActivity : AppCompatActivity() {
    private lateinit var returnButton:      Button
    private lateinit var saveButton:        Button
    private lateinit var shareButton:       Button
    private lateinit var receivedIntent:    Intent
    private lateinit var showCodeImageView: ImageView
    private lateinit var saveCode: IExport
    private          var creationSucceed:   Boolean    = true




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



        saveButton.setOnClickListener {
            val options = arrayOf(getString(R.string.save_location_local))

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.select_save_option))
                .setItems(options) { _, which ->
                    when (which) {
                        0 -> saveLocally(showCodeImageView.drawable?.toBitmap(), codeText)
                    }
                }.setNegativeButton(getString(R.string.quit)) { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }



        shareButton.setOnClickListener{
            shareImage()
        }



        returnButton.setOnClickListener {
            finish()
        }
    }




    private fun saveLocally(data: Bitmap?, information: String?) {
        data?.let {
            saveCode = ExportLocal(it)
            val success = saveCode(data, saveCode, information)
            if (success) {
                Toast.makeText(this, getString(R.string.save_successfully), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, getString(R.string.save_unsuccessfully), Toast.LENGTH_SHORT).show()
            }
        } ?: Toast.makeText(this, getString(R.string.save_unsuccessfully), Toast.LENGTH_SHORT).show()
    }




    private fun saveCode(data: Bitmap, storeObject: IExport, information: String?): Boolean {
        val options = arrayOf("Automatic Naming", "Custom Naming")
        var selectedOption = 0

        storeObject.setData(data)



        if (storeObject.checkAvailability())
        {
            if(storeObject.export(information))
            {
                return true
            }
        }



        return false
    }




    private fun generateQRCode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 600, 400)
            showCodeImageView.setImageBitmap(bitmap)
            creationSucceed = true
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




    private fun generateBarcode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.CODE_128, 800, 400)
            showCodeImageView.setImageBitmap(bitmap)
            creationSucceed = true
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




    private fun shareImage() {
        val bitmap = showCodeImageView.drawable?.toBitmap()

        if (bitmap != null) {
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
        } else {
            return
        }
    }
}