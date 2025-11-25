package ru.anlyashenko.fragmentapp.view

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentImagesBinding
import ru.anlyashenko.fragmentapp.model.MediaImage
import kotlin.concurrent.thread


class ImagesFragment : Fragment() {

    private var _binding: FragmentImagesBinding? = null
    private val binding
        get() = _binding!!

    private val adapter = PhotosAdapter()
    private val images = mutableListOf<MediaImage>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            loadImages()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()

        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun loadImages() {
        thread {
            val projection = arrayOf(
                    MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME
            )

            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val sort = "${MediaStore.Images.Media.DATE_ADDED} DESC"

            val cursor = requireContext().contentResolver.query(
                uri,
                projection,
                null,
                null,
                sort
            )

            cursor?.use {
                val tempList = mutableListOf<MediaImage>()
                val columnIndexId = it.getColumnIndex(MediaStore.Images.Media._ID)
                val columnIndexName = it.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)

                while (it.moveToNext()) {
                    val id = it.getLong(columnIndexId)
                    val name = it.getString(columnIndexName)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )
                    tempList.add(MediaImage(id, contentUri, name))
                }
                images.clear()
                images.addAll(tempList)

                view?.post {
                    adapter.submitList(images)
                }
            }
        }

    }

    private fun checkPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(requireContext(), permission)
            == PackageManager.PERMISSION_GRANTED
        ) {
            loadImages()
        } else {
            requestPermissionLauncher.launch(permission)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}