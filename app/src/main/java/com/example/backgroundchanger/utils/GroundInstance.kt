package com.example.backgroundchanger.utils

import android.graphics.Bitmap

class GroundInstance {

    var backupImage: Bitmap? = null
    var foregroundImage: Bitmap? = null
    var backgroundImage: Bitmap? = null

    companion object {
        private var instance: GroundInstance? = null

        fun getInstance(): GroundInstance? {
            if (instance == null) instance = GroundInstance()
            return instance
        }
    }
}