package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.anlyashenko.fragmentapp.databinding.FragmentThreeBinding
import java.io.File

class FragmentThree : Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentThreeBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val file = File(requireContext().filesDir, NOTE_FILENAME)

        binding.btnSaveNote.setOnClickListener {
            val textToSave = binding.etAddNote.text.toString()
            file.writeText(textToSave) // тут под капотом уже используется .use

            // но можно написать еще так
//            file.writer().use {
//                it.write(textToSave)
//            }

            Toast.makeText(requireContext(), "Текст сохранён!", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoadNote.setOnClickListener {

            // проверка на существование файла
            if (file.exists()) {
                val readContent = file.readText() // также используется .use под капотом

                // расширенная версия
//                val readContent = file.reader().use { it.readText() }

                binding.etAddNote.setText(readContent)
                Toast.makeText(requireContext(), "Запись загружена!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Записи не существует", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDeleteNote.setOnClickListener {
            if (file.exists()) {
                file.delete()
                binding.etAddNote.setText("")
                Toast.makeText(requireContext(), "Запись удалена!", Toast.LENGTH_SHORT).show()
            } else
                Toast.makeText(requireContext(), "Нечего удалять", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NOTE_FILENAME = "my_private_note.txt"
    }

}