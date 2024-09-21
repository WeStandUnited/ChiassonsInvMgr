package com.chiassons.barcodescanner
import android.graphics.Bitmap
import com.google.mlkit.vision.barcode.Barcode

data class tcItem(
    var itemBarcode: Barcode,
    var quantity : Int,
    var description: String,
    var itemImage: Bitmap,
)
