package com.chiassons.barcodescanner

import androidx.activity.ComponentActivity
import android.Manifest
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy

class MainActivity : ComponentActivity() {

    private lateinit var previewView: PreviewView
    private lateinit var gBarCodeView: TextView
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var gScanButton: Button
    lateinit var gBarcode: Barcode


    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                startCamera()
            } else {
                Log.e("MainActivity", "Camera permission denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        previewView = findViewById(R.id.previewView)
        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        gScanButton = findViewById(R.id.gScanButton)
        gScanButton.backgroundTintList = ColorStateList.valueOf(Color.RED)
        gScanButton.setOnClickListener {
            try {
                SwitchToInvView(gBarcode)
            } catch(e : Exception){
                gScanButton.backgroundTintList = ColorStateList.valueOf(Color.RED)
                // Put like a .5 sec sleep here?
                Log.d("gScanButton","Barcode null")
                gScanButton.backgroundTintList = ColorStateList.valueOf(Color.WHITE)

            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetResolution(android.util.Size(1280, 720))
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer())
                }

            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun SwitchToInvView(barcode: Barcode)
    {
        if (barcode.format != -1 ) // -1 returns unknown barcode format
        {
            val lcCache = BarcodeRegistry.getAllEntries()
            val intent = Intent(this, tcInventoryMgr::class.java)
            intent.putExtra("BarCode", barcode.rawValue)
            intent.putExtra("BarCodeFormat", getBarcodeFormatString(barcode.format))
            intent.putExtra("Item-Id", lcCache[barcode])
            startActivity(intent)
        }
    }
    private fun getBarcodeFormatString(format: Int): String {
        return when (format) {
            Barcode.FORMAT_CODE_39 -> "FORMAT_CODE_39"
            Barcode.FORMAT_CODE_93 -> "FORMAT_CODE_93"
            Barcode.FORMAT_CODE_128 -> "FORMAT_CODE_128"
            Barcode.FORMAT_EAN_8 -> "FORMAT_EAN_8"
            Barcode.FORMAT_EAN_13 -> "FORMAT_EAN_13"
            Barcode.FORMAT_ITF -> "FORMAT_ITF"
            Barcode.FORMAT_UPC_A -> "FORMAT_UPC_A"
            Barcode.FORMAT_UPC_E -> "FORMAT_UPC_E"
            Barcode.FORMAT_AZTEC -> "FORMAT_AZTEC"
            Barcode.FORMAT_DATA_MATRIX -> "FORMAT_DATA_MATRIX"
            Barcode.FORMAT_PDF417 -> "FORMAT_PDF417"
            Barcode.FORMAT_QR_CODE -> "FORMAT_QR_CODE"
            else -> "Unknown"
        }
    }
    inner class BarcodeAnalyzer : ImageAnalysis.Analyzer {
        @OptIn(ExperimentalGetImage::class)
        override fun analyze(image: ImageProxy) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val mediaImage = image.image ?: return
            val inputImage = InputImage.fromMediaImage(mediaImage, rotationDegrees)

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_CODE_39,
                    Barcode.FORMAT_CODE_93,
                    Barcode.FORMAT_CODE_128,
                    Barcode.FORMAT_EAN_8,
                    Barcode.FORMAT_EAN_13,
                    Barcode.FORMAT_ITF,
                    Barcode.FORMAT_UPC_A,
                    Barcode.FORMAT_UPC_E,
                    Barcode.FORMAT_AZTEC,
                    Barcode.FORMAT_DATA_MATRIX,
                    Barcode.FORMAT_PDF417,
                    Barcode.FORMAT_QR_CODE
                )
                .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        if (barcode.rawValue != null) {
                            gBarcode = barcode;
                            gScanButton.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                        }
                        else
                        {
                            gScanButton.backgroundTintList = ColorStateList.valueOf(Color.RED)
                        }


                    }
                }
                .addOnFailureListener {
                    Log.e("BarcodeAnalyzer", "Barcode scanning failed", it)
                }
                .addOnCompleteListener {
                    image.close()
                }
        }
    }

}
