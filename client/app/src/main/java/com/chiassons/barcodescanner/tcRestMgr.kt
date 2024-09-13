package com.chiassons.barcodescanner

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class tcRestMgr(private val baseUrl: String) {

    private val client = OkHttpClient()

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    suspend fun get(endpoint: String): String? {
        val request = Request.Builder()
            .url("$baseUrl$endpoint")
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        }
    }

    suspend fun post(endpoint: String, jsonBody: Any): String? {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = moshi.adapter(Any::class.java).toJson(jsonBody).toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$baseUrl$endpoint")
            .post(body)
            .build()

        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        }
    }
}
