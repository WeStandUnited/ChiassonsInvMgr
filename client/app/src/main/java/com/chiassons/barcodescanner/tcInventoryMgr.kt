package com.chiassons.barcodescanner

import androidx.activity.ComponentActivity
import android.os.Bundle
import android.Manifest
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.Barcode.BarcodeValueType

class tcInventoryMgr : AppCompatActivity() {

    var gItemCache : HashMap<Pair<String, String>, String>
            = HashMap<Pair<String, String>, String> ();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inv_mgr)
        val barCodeID = intent.getStringExtra("BarCode")
        val barcodeFormat = intent.getStringExtra("BarCodeFormat")
        Log.d("BarcodeAnalyzer", "Barcode detected: " + barCodeID + ":" + barcodeFormat)

        // Use the value to send a get request to server


        // If GET returns null
        // then open adder menu

    }

}