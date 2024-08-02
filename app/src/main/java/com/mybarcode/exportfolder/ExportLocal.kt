package com.mybarcode.exportfolder




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
        return data.byteCount.toLong()
    }




    private fun getAvailableInternalMemorySizeSpace(): Long {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val availableBlocks = stat.availableBlocksLong
        return availableBlocks * blockSize
    }




    /**
     * @return a boolean which says if the source is available. In this case it checks if the phone has enough capacities to store the image.
     */
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
        val folder = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), folderName)

        if (!folder.exists()) {
            folder.mkdirs()
        }

        val file = File(folder, truncatedFileName(fileName))

        return try {
            FileOutputStream(file).use { out ->
                data.compress(Bitmap.CompressFormat.JPEG, 1000000, out)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun truncatedFileName(fileName: String): String {
        return if (fileName.length > 90) {
            fileName.subSequence(0, 89).toString()
        } else {
            fileName
        }

    }



    private fun sanitizeFileName(fileName: String): String {
        // Replace invalid characters in file name with underscores
        return fileName.replace("[^a-zA-Z0-9.-]".toRegex(), "_")
    }

    private fun getExternalFilesDir(type: String): File? {
        return Environment.getExternalStoragePublicDirectory(type)
    }
}