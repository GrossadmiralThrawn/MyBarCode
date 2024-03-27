package com.example.mybarcode.export




import android.graphics.Bitmap
import android.os.Environment
import android.os.StatFs
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SaveLocal(private var data: Bitmap) : IExport {
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





    override fun export(): Boolean {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val folderName = "MyBarcodeData"
        val fileName = "barcode_$timeStamp.jpg"
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
}