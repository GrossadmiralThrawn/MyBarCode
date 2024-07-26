package com.mybarcode.importfolder




import android.graphics.Bitmap




interface IImport {
    fun getData(): List<String>
    fun checkAvailability(): Boolean
    fun importCode(fileName: String): Bitmap?
    fun deleteCode(code: String): Boolean
}