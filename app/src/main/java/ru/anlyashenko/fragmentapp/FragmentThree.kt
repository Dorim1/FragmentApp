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
        val file = File(requireContext().filesDir, TASKS_FILENAME)
        setupRecyclerView()
        loadTasks()

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
        const val TASKS_FILENAME = "tasks.json"
    }

}