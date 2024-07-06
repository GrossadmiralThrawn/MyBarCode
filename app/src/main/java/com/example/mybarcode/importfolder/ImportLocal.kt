package com.example.mybarcode.importfolder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File

class ImportLocal: IImport {
    override fun getData(): List<String> {
        val folderName = "MyBarcodeData"
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName)
        val codeList = mutableListOf<String>()

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()
            files?.forEach { file ->
                // Check if it's a valid image file (you may want to refine this check)
                if (file.isFile && file.extension.equals("jpg", ignoreCase = true)) {
                    val code = file.nameWithoutExtension // Extract filename without extension
                    codeList.add(code)
                }
            }
        }

        return codeList
    }



    override fun checkAvailability(): Boolean {
        val folderName = "MyBarcodeData"
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName)
        return folder.exists() && folder.isDirectory
    }

    override fun importCode(fileName: String): Bitmap? {
        val folderName = "MyBarcodeData"
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "$folderName/$fileName.jpg")
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }
}