package ru.anlyashenko.fragmentapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.anlyashenko.fragmentapp.receivers.AirplaneModeReceiver
import ru.anlyashenko.fragmentapp.databinding.FragmentBroadcastBinding
import ru.anlyashenko.fragmentapp.receivers.ACTION_CUSTOM_EVENT

class BroadcastFragment : Fragment() {
    private var _binding: FragmentBroadcastBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentBroadcastBinding must not be null")

    private val airplaneReceiver = AirplaneModeReceiver { isTurnedOn ->
        if (isTurnedOn) {
            binding.ivIsOn.visibility = View.VISIBLE
            Toast.makeText(requireContext(), "Режим полёта включён!", Toast.LENGTH_SHORT).show()
        } else {
            binding.ivIsOn.visibility = View.GONE
            Toast.makeText(requireContext(), "Режим полёта выключен!!", Toast.LENGTH_SHORT).show()
        }
    }

    private val customEventReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == ACTION_CUSTOM_EVENT) {
                val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Пусто"
                Toast.makeText(requireContext(), "Сообщение: $message", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onStart() {
        super.onStart()
        requireActivity().registerReceiver(
            airplaneReceiver,
            IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        )

        val filter = IntentFilter(ACTION_CUSTOM_EVENT)
        ContextCompat.registerReceiver(
            requireActivity(),
            customEventReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
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
        binding.btnSendAction.setOnClickListener {
            sendBroadcast(requireContext())
        }

    }

    fun sendBroadcast(context: Context) {
        val intent = Intent(ACTION_CUSTOM_EVENT)
        intent.putExtra("EXTRA_MESSAGE", "Загрузка завершена успешно!")
        intent.setPackage(context.packageName)
        context.sendBroadcast(intent)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().unregisterReceiver(airplaneReceiver)
        requireActivity().unregisterReceiver(customEventReceiver)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

