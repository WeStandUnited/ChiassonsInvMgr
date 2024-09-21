package com.chiassons.barcodescanner
import android.graphics.Bitmap
import com.google.mlkit.vision.barcode.Barcode
import com.squareup.moshi.Json

data class tcItem(
    @Json(name = "BarCodeNumber") var itemRawValue: String,
    @Json(name = "BarCodeFormat") var itemFormatValue: String,
    @Json(name = "ItemId") var itemId : String?,
    @Json(name = "Quantity")  var quantity : Int?,
    @Json(name = "Description")  var description: String?,
    var itemImage: Bitmap?,
    var tags : List<String>?,

)

