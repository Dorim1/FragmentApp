package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.anlyashenko.fragmentapp.databinding.FragmentOneBinding


class FragmentOne : Fragment() {

    private var _binding: FragmentOneBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binging for FragmentOneBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()

        binding.btnToFragment2.setOnClickListener { navController.navigate(R.id.fragmentTwo) }
        binding.btnToFragment3.setOnClickListener { navController.navigate(R.id.fragmentThree) }
        binding.btnToFragment4.setOnClickListener { navController.navigate(R.id.fragmentFour) }
    }

}