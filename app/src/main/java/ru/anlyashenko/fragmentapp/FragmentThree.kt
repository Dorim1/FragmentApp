package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.anlyashenko.fragmentapp.databinding.FragmentThreeBinding
import java.io.File

class FragmentThree : Fragment() {

    private var _binding: FragmentThreeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentThreeBinding must not be null")

    private val gson = Gson()
    private lateinit var listAdapter: ListAdapter
    private val tasks = mutableListOf<Task>()

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
        setupRecyclerView()
        loadTasks()

        binding.btnSaveNote.setOnClickListener {
            val ageToSave = binding.etInputAge.text.toString().toIntOrNull() ?: 0
            val nameToSave = binding.etInputName.text.toString()

            val user = User(
                age = ageToSave,
                name = nameToSave,
            )

            val gsonString = Gson().toJson(user)
            file.writeText(gsonString) // тут под капотом уже используется .use

            // но можно написать еще так
//            file.writer().use {
//                it.write(textToSave)
//            }

            Toast.makeText(requireContext(), "User сохранен!", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoadNote.setOnClickListener {
            // проверка на существование файла
            if (file.exists()) {
                val readContent = file.readText() // также используется .use под капотом

                // расширенная версия
//                val readContent = file.reader().use { it.readText() }

                val userObject = gson.fromJson(readContent, User::class.java)
                binding.etAddNote.setText(readContent)
                binding.etInputAge.setText(userObject.age.toString())
                binding.etInputName.setText(userObject.name)
                Toast.makeText(requireContext(), "User загружен!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "User не существует", Toast.LENGTH_SHORT).show()
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

        binding.btnAddNote.setOnClickListener {
            val taskText = binding.etAddNote.text.toString()
            if (taskText.isNotBlank()) {
                val newTask = Task(text = taskText, isDone = false)
                listAdapter.addTask(newTask)
                binding.etAddNote.text.clear()
                saveTask()
            }
        }


    }

    private fun setupRecyclerView() {
        listAdapter = ListAdapter(tasks) {
            saveTask()
        }
        binding.rvListNotes.adapter = listAdapter
        binding.rvListNotes.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun saveTask() {
        val jsonString = gson.toJson(tasks)
        val file = File(requireContext().filesDir, TASKS_FILENAME)
        file.writeText(jsonString)
    }

    private fun loadTasks() {
        val file = File(requireContext().filesDir, TASKS_FILENAME)
        if (file.exists()) {
            val jsonString = file.readText()
            val type = object : TypeToken<List<Task>>() {}.type
            val loadedTasks: List<Task> = gson.fromJson(jsonString, type)
            listAdapter.setTasks(loadedTasks)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NOTE_FILENAME = "my_private_note.txt"
        const val TASKS_FILENAME = "tasks.json"
    }

}