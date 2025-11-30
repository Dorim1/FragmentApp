package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.anlyashenko.fragmentapp.databinding.FragmentInflationBinding

class FragmentInflation : Fragment() {

    private var _binding: FragmentInflationBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentInflationBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInflationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        binding.btnOpenFragmentFive.setOnClickListener {
            navController.navigate(R.id.action_fragmentFour_to_fragmentFive)
        }

        binding.btnAutoAdd.setOnClickListener {
            layoutInflater.inflate(R.layout.item_experiment, binding.llContainer, true)
        }

        binding.btnManualCorrect.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.item_experiment, binding.llContainer, false)
            binding.llContainer.addView(view)
        }

        binding.btnManualWrong.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.item_experiment, null)
            binding.llContainer.addView(view)
        }

        binding.btnClear.setOnClickListener {
            binding.llContainer.removeAllViews()
        }

        binding.profileHeader.setProfileName("testik")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}