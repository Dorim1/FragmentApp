package ru.anlyashenko.fragmentapp.view

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.anlyashenko.fragmentapp.receivers.AirplaneModeReceiver
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentBroadcastBinding

class BroadcastFragment : Fragment() {
    private var _binding: FragmentBroadcastBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentBroadcastBinding must not be null")

    private val receiver = AirplaneModeReceiver { isTurnedOn ->
        if (isTurnedOn) {
            binding.ivIsOn.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Режим полёта включён!", Toast.LENGTH_SHORT).show()
        } else {
            binding.ivIsOn.visibility = View.GONE
            Toast.makeText(requireContext(), "Режим полёта выключен!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        requireActivity().registerReceiver(receiver, intentFilter)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBroadcastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}