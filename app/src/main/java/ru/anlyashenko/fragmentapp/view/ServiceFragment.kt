package ru.anlyashenko.fragmentapp.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentServiceBinding
import ru.anlyashenko.fragmentapp.services.StartedService

class ServiceFragment : Fragment() {

    private var _binding: FragmentServiceBinding? = null
    private val binding
        get() = _binding!!

    private var isBound = false
    private var service: StartedService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            val binder = service as StartedService.LocalBinder
            this@ServiceFragment.service = binder.getService()
            isBound = true

            this@ServiceFragment.service?.onProgressChanged = { progress ->
                binding.root.post {
                    binding.progressBar.progress = progress
                }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
            isBound = false
        }

    }
    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startService()
        } else {
            Toast.makeText(requireContext(), "Нужно разрешение на уведомления!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(requireContext(), StartedService::class.java)
        requireActivity().bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createNotificationChannel()

        binding.btnStartService.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    startService()
                } else {
                    requestPermissionsLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                startService()
            }
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "service_channel"
            val channelName = "Канал сервиса"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelId, channelName, importance)
            channel.description = "Системный канал для фонового сервиса"
            val notificationManager = getSystemService(requireContext(), NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun startService() {
        Log.d("ServiceFragment", "send the startService command")
        val intent = Intent(requireContext(), StartedService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            service?.onProgressChanged = null
            requireActivity().unbindService(connection)
            isBound = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}