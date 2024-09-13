package com.chiassons.barcodescanner

import androidx.activity.ComponentActivity
import android.os.Bundle
import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.Barcode.BarcodeValueType

class tcInventoryMgr : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inv_mgr)
        val BarCodeID = intent.getStringExtra("BarCode")
        val BarcodeValueType = intent.getStringExtra("BarCodeType")

        // Use the value to send a get request to server


        // If GET returns null
        // then open adder menu

    }
}