package com.example.backgroundchanger.fragment

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.core.graphics.drawable.toBitmap
import com.example.backgroundchanger.R
import com.example.backgroundchanger.databinding.FragmentImageModeBinding
import com.example.backgroundchanger.utils.GroundInstance
import com.example.backgroundchanger.utils.ImageUtils
import com.example.backgroundchanger.utils.LoadingDialog
import com.example.backgroundchanger.utils.PermissionCheckerUtils
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ImageModeFragment : Fragment(), OnClickListener {

    private var _binding: FragmentImageModeBinding? = null
    private val binding get() = _binding!!

    private lateinit var loading: LoadingDialog
    private lateinit var chip: Chip
    private lateinit var buttonSegment : Button
    private lateinit var buttonCamera : FloatingActionButton
    private lateinit var buttonAddGallery : FloatingActionButton
    private lateinit var buttonBackup : FloatingActionButton
    private lateinit var buttonDownload : FloatingActionButton
    private lateinit var imageViewUri : Uri
    private lateinit var selectedBitmap : Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageModeBinding.inflate(inflater, container, false)
        loading = LoadingDialog(requireActivity(), inflater)

        binding.apply {
            chip.isChecked = true
            chip.setOnCheckedChangeListener { button, isChecked ->
                if (isChecked) {
                    button.text = "Foreground Mode"
                    mode = !mode
                }
                else {
                    button.text = "Background Mode"
                    mode = !mode
                }
            }
        }
        buttonSegment.setOnClickListener(this@ImageModeFragment)
        buttonCamera.setOnClickListener(this@ImageModeFragment)
        buttonBackup.setOnClickListener(this@ImageModeFragment)
        buttonDownload.setOnClickListener(this@ImageModeFragment)
        buttonAddGallery.setOnClickListener(this@ImageModeFragment)

        return binding.root
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.buttonSegment -> {
                loading.show()
                ImageAnalyzer().analyze(binding.imageView.drawable.toBitmap())
                Handler(Looper.getMainLooper()).postDelayed({
                   binding.imageView.setImageBitmap(GroundInstance.getInstance()?.foregroundImage)
                }, 2000)
            }
            R.id.buttonCamera -> {
                PermissionCheckerUtils.checkCameraPerm(requireContext(), requireActivity()) {
                    imageViewUri = ImageUtils.getImageUriFromBitmap(requireContext(), binding.imageView.drawable.toBitmap())
                }
            }
            R.id.buttonAddGallery -> {
                PermissionCheckerUtils.checkGalleryReadPerm(requireContext(), requireActivity()) {

                }
            }
            R.id.buttonBackup -> {
                binding.imageView.setImageResource(0)
                binding.imageView.setImageBitmap(GroundInstance.getInstance()?.backupImage)
            }
            R.id.buttonDownload -> {
                PermissionCheckerUtils.checkGalleryWritePerm(requireContext(), requireActivity()) {
                    ImageUtils.download(binding.imageView.drawable.toBitmap(), requireContext())
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        var mode: Boolean = true
    }
}