package com.example.mybarcode.exportfolder




import android.graphics.Bitmap
import android.os.Environment
import android.os.StatFs
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ExportLocal(private var data: Bitmap) : IExport {
    override fun setData(newData: Bitmap) {
        data = newData
    }




    private fun getDataSize(): Long {
        return data.getByteCount().toLong()
    }





    private fun getAvailableInternalMemorySizeSpace(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return availableBlocks * blockSize
    }




    override fun checkAvailability(): Boolean {
        return getDataSize() < getAvailableInternalMemorySizeSpace()
    }





    override fun export(information: String?): Boolean {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val folderName = "MyBarcodeData"
        val fileName = if (!information.isNullOrEmpty()) {
            "${sanitizeFileName(information)}_$timeStamp.jpg"
        } else {
            "barcode_$timeStamp.jpg"
        }
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName)

        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = File(folder, fileName)

        return try {
            FileOutputStream(file).use { out ->
                data.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun sanitizeFileName(fileName: String): String {
        // Replace invalid characters in file name with underscores
        return fileName.replace("[^a-zA-Z0-9.-]".toRegex(), "_")
    }
}