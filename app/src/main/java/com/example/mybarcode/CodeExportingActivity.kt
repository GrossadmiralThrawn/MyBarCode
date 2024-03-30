package com.example.mybarcode




import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.mybarcode.importfolder.IImport
import com.example.mybarcode.importfolder.ImportLocal


class CodeExportingActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_exporting)
        val importData: IImport
        val options = arrayOf(getString(R.string.local_location))         // Erstelle ein Array mit den Dropdown-Menü-Optionen
        spinner     = findViewById(R.id.spinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)         // Erstelle ein ArrayAdapter mit den Dropdown-Menü-Optionen und einem Standardlayout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)         // Setze das Layout für den Dropdown-Menü-Popup


        spinner.adapter = adapter


        when (spinner.selectedItemPosition)
        {
            0 -> {
                importData = ImportLocal()  // Hier importData mit ExportLocal initialisieren
                availableCodes(importData)
            }
        }
    }




    private fun availableCodes(importData: IImport) {
        
    }
}