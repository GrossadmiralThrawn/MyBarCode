package com.example.mybarcode.importfolder




import android.graphics.Bitmap




interface IImport {
    fun getData(): List<String>
    fun checkAvailability(): Boolean
    fun importCode(): Bitmap
}