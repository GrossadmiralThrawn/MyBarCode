package com.example.mybarcode.importfolder




import android.graphics.Bitmap




interface IImport {
    fun getData(): Bitmap
    fun checkAvailability(): Boolean
    fun importCode(): Bitmap
}