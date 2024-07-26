package com.mybarcode.importfolder

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
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




    override fun deleteCode(code: String): Boolean {
        val folderName = "MyBarcodeData"
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName)

        if (folder.exists() && folder.isDirectory) {
            val files = folder.listFiles()

            // Find the file to delete
            var targetFile: File? = null
            files?.forEach { file ->
                if (file.isFile && file.nameWithoutExtension.equals(code, ignoreCase = true)) {
                    targetFile = file
                }
            }

            if (targetFile != null) {
                val success = targetFile!!.delete()

                // If the deletion was successful, update the available codes list and call the callback function
                if (success) {
                    val newCodeList = mutableListOf<String>()

                    files?.forEach { file ->
                        if (file.isFile && file.nameWithoutExtension.equals(code, ignoreCase = true)) return@forEach

                        // Check if it's a valid image file (you may want to refine this check)
                        if (file.isFile && file.extension.equals("jpg", ignoreCase = true)) {
                            val newCode = file.nameWithoutExtension
                            newCodeList.add(newCode)
                        }
                    }
                    return true
                } else {
                    // If the deletion failed, log an error message
                    Log.e("ImportLocal", "Failed to delete code $code")
                    return false
                }
            } else {
                // If no matching file was found, do nothing and return
                return true
            }
        } else {
            // If the folder does not exist or is not a directory, log an error message
            Log.e("ImportLocal", "Folder 'MyBarcodeData' does not exist or is not a directory")
            return true
        }
    }

}