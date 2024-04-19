package com.example.backgroundchanger.fragment

import android.graphics.Bitmap
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.backgroundchanger.utils.DrawOverlay
import com.example.backgroundchanger.utils.SegmenterOptions
import com.google.mlkit.vision.common.InputImage

class StreamAnalyzer(
    private val drawOverlay: DrawOverlay
) : ImageAnalysis.Analyzer {
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            SegmenterOptions.streamSegmenter().process(image)
                .addOnSuccessListener {
                    val mask = it.buffer
                    val maskWidth = it.width
                    val maskHeight = it.height
                    mask.rewind()

                    val bitmap = Bitmap.createBitmap(maskWidth, maskHeight, Bitmap.Config.ARGB_8888)

                    drawOverlay.segment = bitmap
                    drawOverlay.invalidate()
                }
                .addOnFailureListener{
                    imageProxy.close()
                }
        }
    }
}