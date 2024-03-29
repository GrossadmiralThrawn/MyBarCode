package com.example.mybarcode.exportfolder




import android.graphics.Bitmap




interface IExport {
    fun setData(newData: Bitmap)
    fun checkAvailability(): Boolean
    fun export(information: String?): Boolean
}