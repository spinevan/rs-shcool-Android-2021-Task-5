package com.example.android2021task5.ui.main

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.android2021task5.databinding.FragmentCatImageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CatImageFragment : Fragment() {

    private var _binding: FragmentCatImageBinding? = null
    private val binding get() = _binding!!

    private var imgUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imgUrl = it.getString("imgUrl")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.root)
            .load(imgUrl)
            .into(binding.fullCatImage)

        binding.saveToGallaryBtn.setOnClickListener{
            if (!verifyPermissions()) {
                return@setOnClickListener
            }

            println(imgUrl)

            val filename =
                imgUrl?.lastIndexOf('/')?.plus(1)?.let { it1 -> imgUrl?.length?.let { it2 ->
                    imgUrl?.substring(it1,
                        it2
                    )
                } }

            CoroutineScope(Dispatchers.IO).launch {
                if (filename != null) {
                    val savedFile = saveImage(Glide.with(binding.root)
                        .asBitmap()
                        .load(imgUrl)
                        .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                        .error(android.R.drawable.stat_notify_error)
                        .submit()
                        .get(), filename)
                    if ( savedFile?.length!! > 0 ) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun saveImage(image: Bitmap, imageFileName: String): String? {
        var savedImagePath: String? = null
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/"+getString(com.example.android2021task5.R.string.app_name)
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return savedImagePath
    }

    private fun verifyPermissions(): Boolean {

        // This will return the current Status
        val permissionExternalMemory =
            context?.let { ActivityCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) }
        if (permissionExternalMemory != PackageManager.PERMISSION_GRANTED) {
            val STORAGE_PERMISSIONS = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            // If permission not granted then ask for permission real time.
            ActivityCompat.requestPermissions(requireActivity(), STORAGE_PERMISSIONS, 1)
            return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CatImageFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}