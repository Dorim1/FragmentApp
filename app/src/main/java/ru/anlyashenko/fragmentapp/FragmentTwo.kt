package ru.anlyashenko.fragmentapp

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import ru.anlyashenko.fragmentapp.databinding.FragmentTwoBinding
import java.io.File


class FragmentTwo : Fragment() {

    private var _binding: FragmentTwoBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentTwoBinding must not be null")

    private val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val file = File(requireContext().filesDir, NOTE_FILENAME)

        binding.btnSaveNote.setOnClickListener {
            val ageToSave = binding.etInputAge.text.toString().toIntOrNull() ?: 0
            val nameToSave = binding.etInputName.text.toString()

            val user = User(age = ageToSave, name = nameToSave)

            val gsonString = gson.toJson(user)
            file.writeText(gsonString)
            Toast.makeText(requireContext(), "Запись сохранена", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoadNote.setOnClickListener {
            if (file.exists()) {
                val readContent = file.readText()
                val userObject = gson.fromJson(readContent, User::class.java)
                binding.tvDescFragment.text = readContent
                binding.etInputAge.setText(userObject.age.toString())
                binding.etInputName.setText(userObject.name)
                Toast.makeText(requireContext(), "Запись загружена", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Запись не существует", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteNote.setOnClickListener {
            if (file.exists()) {
                file.delete()
                binding.tvDescFragment.text = "Это фрагмент 2"
                Toast.makeText(requireContext(), "Запись удалена", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Нечего удалять", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSaveToGallery.setOnClickListener {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image)
            val newImage = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "image.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/FragmentApp")
            }
            val contentUri = requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, newImage)

            if (contentUri == null) {
                Toast.makeText(requireContext(), "Не удалось создать файл", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            requireContext().contentResolver.openOutputStream(contentUri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            }
            Toast.makeText(requireContext(), "Изображение сохранено!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val NOTE_FILENAME = "my_private_note.txt"
    }

}