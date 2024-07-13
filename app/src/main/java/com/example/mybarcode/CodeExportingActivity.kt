package com.example.mybarcode




import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mybarcode.importfolder.IImport
import com.example.mybarcode.importfolder.ImportLocal




class CodeExportingActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var listView: ListView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_exporting)

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

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCode = codeList[position]
            val bitmap = importData.importCode(selectedCode)
            if (bitmap != null) {
                val dialog = BitmapDialogFragment.newInstance(bitmap)
                dialog.show(supportFragmentManager, "BitmapDialogFragment")
            } else {
                Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show()
            }
        }
    }
}