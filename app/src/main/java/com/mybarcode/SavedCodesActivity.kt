package com.mybarcode


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mybarcode.R
import com.mybarcode.importfolder.IImport
import com.mybarcode.importfolder.ImportLocal


class SavedCodesActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_codes)

        spinner = findViewById(R.id.spinner)
        listView = findViewById(R.id.listView)

        val options = arrayOf(getString(R.string.local_location))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        when (spinner.selectedItemPosition) {
            0 -> {
                val importData = ImportLocal()
                availableCodes(importData)
            }
        }
    }


    private fun availableCodes(importData: IImport) {
        val codeList = importData.getData()
        val listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, codeList)
        listView.adapter = listAdapter

        var lastClickTime = 0L

        listView.setOnItemClickListener { _, _, position, _ ->
            val currentTime = System.currentTimeMillis()

            // Prüfe, ob der Benutzer innerhalb von 2 Sekunden geklickt hat (und nicht nur auf die Liste geblättert)
            if (currentTime - lastClickTime > 1999) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Löschen")
                    .setMessage("Soll das Element wirklich gelöscht werden?")
                    .setPositiveButton("Ja") { _, _ ->
                        // Hier kannst du den Code löschen
                        importData.deleteCode(codeList[position])
                        availableCodes(importData) // Aktualisiere die Liste nach dem Löschen des Codes
                    }
                    .setNegativeButton("Nein", null)
                    .show()
            } else {
                val selectedCode = codeList[position]
                val bitmap = importData.importCode(selectedCode)

                if (bitmap != null) {
                    val dialog = BitmapDialogFragment.newInstance(bitmap)
                    dialog.show(supportFragmentManager, "BitmapDialogFragment")
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.image_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // Zeitpunkt des aktuellen Klicks
            lastClickTime = currentTime
        }
    }
}