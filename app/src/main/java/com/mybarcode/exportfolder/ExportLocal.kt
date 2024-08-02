package com.mybarcode.exportfolder

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.os.StatFs
import android.provider.MediaStore
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExportLocal(private var data: Bitmap, private val context: Context) : IExport {

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
        val fileName = if (!information.isNullOrEmpty()) {
            "${sanitizeFileName(information)}_$timeStamp.jpg"
        } else {
            "barcode_$timeStamp.jpg"
        }

        // Use MediaStore to save the image
        val resolver: ContentResolver = context.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MyBarcodeData")
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return uri?.let {
            try {
                resolver.openOutputStream(it).use { outputStream ->
                    data.compress(Bitmap.CompressFormat.JPEG, 100, outputStream!!)
                }
                true
            } catch (e: IOException) {
                e.printStackTrace()
                false
            }
        } ?: false
    }

    private fun sanitizeFileName(fileName: String): String {
        // Replace invalid characters in file name with underscores
        return fileName.replace("[^a-zA-Z0-9.-]".toRegex(), "_")
    }
}
