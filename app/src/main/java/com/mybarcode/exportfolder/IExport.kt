package com.example.mybarcode.exportfolder




import android.graphics.Bitmap


/**
 * An interface which allows to save bar- or qr-codes.
 */
interface IExport {
    /**
     * @param newData is a new bitmap which allows to reuse an object of an implementation of this interface to create mor reusable objects
     */
    fun setData(newData: Bitmap)
    /**
     * @return a boolean which says if the source is available.@return a boolean which says if the source is available.
     */
    fun checkAvailability(): Boolean
    /**
     * Exports data if possible. This function takes a file name.
     */
    fun export(information: String?): Boolean
}