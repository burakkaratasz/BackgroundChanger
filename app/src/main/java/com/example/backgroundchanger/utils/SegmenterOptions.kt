package com.example.backgroundchanger.utils

import com.google.mlkit.vision.segmentation.Segmentation
import com.google.mlkit.vision.segmentation.Segmenter
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions

object SegmenterOptions {

    fun streamSegmenter(): Segmenter { //onlive video modu
        return Segmentation.getClient(SelfieSegmenterOptions.Builder()
            .setDetectorMode(SelfieSegmenterOptions.STREAM_MODE)
            .build())
    }

    fun imageSegmenter(): Segmenter { //fotoğraf modu
        return Segmentation.getClient(SelfieSegmenterOptions.Builder()
            .setDetectorMode(SelfieSegmenterOptions.SINGLE_IMAGE_MODE)
            .build())
    }


}