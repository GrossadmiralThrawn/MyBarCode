package com.example.mybarcode.export

import android.graphics.Bitmap

interface IExport {
    fun setData(newData: Bitmap)
    fun checkAvailability(): Boolean
    fun export(information: String?): Boolean
}