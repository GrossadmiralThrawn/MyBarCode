package com.mybarcode

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.mybarcode.R

class BitmapDialogFragment : DialogFragment() {
    companion object {
        private const val ARG_BITMAP = "bitmap"

        fun newInstance(bitmap: Bitmap): BitmapDialogFragment {
            val fragment = BitmapDialogFragment()
            val args = bundleOf(ARG_BITMAP to bitmap)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bitmap_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val bitmap = arguments?.getParcelable(ARG_BITMAP, Bitmap::class.java)
        imageView.setImageBitmap(bitmap)
    }
}
