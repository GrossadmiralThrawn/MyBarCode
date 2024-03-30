package com.example.mybarcode.importfolder

import android.graphics.Bitmap
import android.os.Environment
import java.io.File

class ImportLocal: IImport {
    private fun extractCodeFromFile(file: File): String? {
        // Here, you need to implement the logic to extract the code from the image file.
        // For simplicity, let's assume the file name contains the code.
        // You may need a more sophisticated approach based on your actual scenario.
        return file.nameWithoutExtension // Just return the file name without extension as code
    }




    override fun getData(): List<String> {
        val folderName = "MyBarcodeData"
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName)
        val codeList = mutableListOf<String>()

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()
            files?.forEach { file ->
                // Check if it's a valid image file (you may want to refine this check)
                if (file.isFile && file.extension.equals("jpg", ignoreCase = true)) {
                    val code = extractCodeFromFile(file)
                    code?.let {
                        codeList.add(it)
                    }
                }
            }
        }

        return codeList
    }

    override fun checkAvailability(): Boolean {
        TODO("Not yet implemented")
    }

    override fun importCode(): Bitmap {
        TODO("Not yet implemented")
    }
}