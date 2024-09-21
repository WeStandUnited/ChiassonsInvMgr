package com.chiassons.barcodescanner

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class tcRestMgr(private val baseUrl: String) {

    private val client = OkHttpClient()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    fun GetItem(barcodeRequest: tcItem): tcItem {
        // Initialize Moshi
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val jsonAdapter = moshi.adapter(tcItem::class.java)

        // Convert the BarcodeRequest object to a JSON string
        val jsonString = jsonAdapter.toJson(barcodeRequest)

        // Prepare the URL with the JSON parameters as query parameters
        val url = "https://your.api.endpoint/path?" +
                "BarCodeNumber=${barcodeRequest.itemRawValue}&" +
                "BarCodeFormat=${barcodeRequest.itemFormatValue}&" +
                "ItemId=${barcodeRequest.itemId}"

        // Create OkHttpClient instance
        val client = OkHttpClient()

        // Build the GET request
        val request = Request.Builder()
            .url(url)
            .build()

        var response =  try {
            // Execute the request
            client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        //TODO take response and parse it into item
        var item : tcItem =
            tcItem("rawBarCode","barFormat","itemId",0,"",null,null)

        return item
    }
}
