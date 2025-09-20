package ru.anlyashenko.fragmentapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.anlyashenko.fragmentapp.databinding.FragmentListBinding

class ListFragment : Fragment(), OptionsBottomSheetFragment.OptionsListener {

    private var _binding: FragmentListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentListBinding must not be null!")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenNewFragment.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main, DetailFragment())
                .addToBackStack(null)
                .commit()
        }


        binding.btnOpenAddNote.setOnClickListener {
            showLoginDialog()
        }

        binding.btnOpenBottomSheet.setOnClickListener {
            OptionsBottomSheetFragment().show(childFragmentManager, "BottomSheet")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoginDialog() {
        val dialog = LoginDialogFragment()
        dialog.show(parentFragmentManager, "LoginDialog")
    }

    override fun onOptionClicked(optionId: String) {
        Toast.makeText(requireContext(), "Вы выбрали $optionId", Toast.LENGTH_SHORT).show()
    }


}