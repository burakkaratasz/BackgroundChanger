package com.example.backgroundchanger.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


object PermissionCheckerUtils { //izin var mı yoksa iste

    fun checkCameraPerm(
        context: Context, //check etme işlemleri
        activity: Activity, // check etme işlemleri
        process: () -> Unit //fonksiyon girdisi, izin varsa ne yapılacak
        ) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            process()
        }
        else { //kullanıcıdan izin iste
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA), 0)
        }
    }

    fun checkGalleryReadPerm(
        context: Context, //check etme işlemleri
        activity: Activity, // check etme işlemleri
        process: () -> Unit //fonksiyon girdisi, izin varsa ne yapılacak
    ) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            process()
        }
        else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }
    }

    fun checkGalleryWritePerm(
        context: Context, //check etme işlemleri
        activity: Activity, // check etme işlemleri
        process: () -> Unit //fonksiyon girdisi, izin varsa ne yapılacak
    ) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            process()
        }
        else {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
        }
    }
}