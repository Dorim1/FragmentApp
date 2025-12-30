package ru.anlyashenko.fragmentapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.anlyashenko.fragmentapp.databinding.FragmentFiveBinding
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController

class FragmentFive : Fragment() {

    private var _binding: FragmentFiveBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentFiveBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        loadPreferences(prefs)
        loadSettings(prefs)

        binding.btnLogin.setOnClickListener {
            val username = binding.etInputLogin.text.toString()
            val rememberMe = binding.cbRememberMe.isChecked
            prefs.edit {
                if (rememberMe) {
                    putString(KEY_USERNAME, username)
                    putBoolean(KEY_REMEMBER_ME, true)
                } else {
                    remove(KEY_USERNAME)
                    remove(KEY_REMEMBER_ME)
                }
            }
            Toast.makeText(requireContext(), "Вход выполнен", Toast.LENGTH_SHORT).show()
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit {
                putBoolean(KEY_DARK_MODE, isChecked)
            }
        }

        binding.btnOpenDataStoreFragment.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFive_to_settingsDataStoreFragment)
        }
    }

    private fun loadSettings(prefs: SharedPreferences) {
        val isDarkModeEnabled = prefs.getBoolean(KEY_DARK_MODE, false)
        binding.switchDarkMode.isChecked = isDarkModeEnabled
    }

    private fun loadPreferences(prefs: SharedPreferences) {
        val isRemembered = prefs.getBoolean(KEY_REMEMBER_ME, false)
        binding.cbRememberMe.isChecked = isRemembered
        if (isRemembered){
            val savedUsername = prefs.getString(KEY_USERNAME, null)
            binding.etInputLogin.setText(savedUsername)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val PREFS_NAME = "app_settings"
        const val KEY_USERNAME = "key_username"
        const val KEY_REMEMBER_ME = "key_remember_me"
        const val KEY_DARK_MODE = "key_dark_mode"
    }

}