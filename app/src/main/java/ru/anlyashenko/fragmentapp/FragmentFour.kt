package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.anlyashenko.fragmentapp.databinding.FragmentFourBinding

class FragmentFour : Fragment() {

    private var _binding: FragmentFourBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentFourBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFourBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.btnOpenFragmentFive.setOnClickListener {
            navController.navigate(R.id.action_fragmentFour_to_fragmentFive)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}