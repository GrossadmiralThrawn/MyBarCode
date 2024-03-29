package com.example.mybarcode




import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mybarcode.exportfolder.IExport
import com.example.mybarcode.exportfolder.ImportLocal
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder




class ShowCodeActivity : AppCompatActivity() {
    private lateinit var returnButton:      Button
    private lateinit var saveButton:        Button
    private lateinit var shareButton:       Button
    private lateinit var receivedIntent:    Intent
    private lateinit var showCodeImageView: ImageView
    private lateinit var saveCode:          IExport
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




        returnButton.setOnClickListener {
            finish()
        }
    }



    private fun saveToDatabase() {
        Toast.makeText(this, getString(R.string.save_successfully), Toast.LENGTH_SHORT).show()
    }




    private fun saveLocally(data: Bitmap?, information: String?) {
        data?.let {
            saveCode = ImportLocal(it) //Wenn Daten erhalten, dann Übergabe an Funktion
            saveCode(data, saveCode, information)
            Toast.makeText(this, getString(R.string.save_successfully), Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Fehler beim Abrufen der Bitmap", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this, getString(R.string.save_successfully), Toast.LENGTH_SHORT).show()
    }




    private fun saveCode(data: Bitmap, storeObject: IExport, information: String?): Boolean {
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
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400)
            showCodeImageView.setImageBitmap(bitmap)
            creationSucceed = true
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }




    private fun generateBarcode(data: String) {
        val barcodeEncoder = BarcodeEncoder()
        try {
            val bitmap = barcodeEncoder.encodeBitmap(data, BarcodeFormat.CODE_128, 400, 200)
            showCodeImageView.setImageBitmap(bitmap)
            creationSucceed = true
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }
}