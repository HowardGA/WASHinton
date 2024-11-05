package com.example.washinton.feature.camera

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(
    private val context: Context,
    private val onScanCompleted: (String) -> Unit
) : ImageAnalysis.Analyzer {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            scanner.process(
                InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees)
            ).addOnSuccessListener { barcodes ->
                barcodes?.takeIf { it.isNotEmpty() }
                    ?.mapNotNull { it.rawValue }
                    ?.joinToString(",")
                    ?.let { result ->
                        // Pass the scanned result to the callback
                        onScanCompleted(result)
                    }
            }.addOnCompleteListener {
                imageProxy.close()
            }
        }
    }
}
