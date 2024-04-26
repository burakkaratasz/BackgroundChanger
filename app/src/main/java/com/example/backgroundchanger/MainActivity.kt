package com.example.backgroundchanger

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.backgroundchanger.R.id.buttonSegment
import com.example.backgroundchanger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var navController: NavController

    //permissions
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private var isCameraGranted = false
    private var isGalleryReadGranted = false
    private var isGalleryWriteGranted = false
    private var isStream = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        //permissions
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            isCameraGranted = it[android.Manifest.permission.CAMERA] ?: isCameraGranted
            isGalleryReadGranted = it[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isGalleryReadGranted
            isGalleryWriteGranted = it[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isGalleryWriteGranted
        }
        requestPermissions()

        //change mode
        binding.fabChangeMode.setOnClickListener {
            if (isStream) {
                navController.navigate(R.id.action_imageModeFragment_to_streamModeFragment)
                binding.fabChangeMode.setImageResource(R.drawable.icon_stream)
            } else {
                navController.navigate(R.id.action_streamModeFragment_to_imageModeFragment)
                binding.fabChangeMode.setImageResource(R.drawable.icon_image)
            }
            isStream = !isStream
        }
    }
    private fun requestPermissions() {
        isCameraGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        isGalleryReadGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        isGalleryWriteGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        val permissonRequest: MutableList<String> = ArrayList()

        if (!isCameraGranted) permissonRequest.add(Manifest.permission.CAMERA)
        if (!isGalleryWriteGranted) permissonRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (!isGalleryReadGranted) permissonRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissonRequest.isNotEmpty()) permissionLauncher.launch(permissonRequest.toTypedArray())

    }
}