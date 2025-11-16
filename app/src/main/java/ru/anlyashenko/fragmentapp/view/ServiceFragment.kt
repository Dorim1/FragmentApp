package ru.anlyashenko.fragmentapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentServiceBinding
import ru.anlyashenko.fragmentapp.services.StartedService

class ServiceFragment : Fragment() {

    private var _binding : FragmentServiceBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartService.setOnClickListener {
            Log.d("ServiceFragment", "send the startService command")
            val intent = Intent(requireContext(), StartedService::class.java)
            requireActivity().startService(intent)
        }

        binding.btnStopService.setOnClickListener {
            val intent = Intent(requireContext(), StartedService::class.java)
            requireActivity().stopService(intent)
            Toast.makeText(requireContext(), "Сервис отменён", Toast.LENGTH_SHORT).show()
        }

        binding.btnCheckUI.setOnClickListener {
            Toast.makeText(requireContext(), "UI отвечает!", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}