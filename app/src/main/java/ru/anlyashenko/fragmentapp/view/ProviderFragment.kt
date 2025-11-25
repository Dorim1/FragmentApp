package ru.anlyashenko.fragmentapp.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentProviderBinding
import kotlin.concurrent.thread


class ProviderFragment : Fragment() {

    private var _binding: FragmentProviderBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentProviderBinding must not be null")

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getContacts()
        } else {
            Toast.makeText(requireContext(), "Нужно разрешение на доступ к контактам!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSendContacts.setOnClickListener {
            checkPermission()
        }

        binding.btnOpenToFragmentImages.setOnClickListener {
            findNavController().navigate(R.id.action_providerFragment_to_imagesFragment)
        }

    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            getContacts()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun getContacts() {
        thread {
            val cursor = requireContext().contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            cursor?.use {
                while (it.moveToNext()) {
                    val index = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)
                    if (index >= 0) {
                        val name = it.getString(index)
                        Log.d("Contacts", "Contact: $name")
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}