package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import ru.anlyashenko.fragmentapp.databinding.FragmentCounterBinding

class CounterFragment : Fragment() {

    private var _binding: FragmentCounterBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentCounterBinding must not be null")

    private val viewModel: CounterViewModel by viewModels()

//    fun sampleVM() {
//        val viewModelProvider = ViewModelProvider.create(
//            owner = this@CounterFragment,
//            factory = defaultViewModelProviderFactory,
//            extras = CreationExtras.Empty
//        )
//        val counterViewModel = viewModelProvider[CounterViewModel::class.java]
//        val anotherCounterViewModel = viewModelProvider["another", CounterViewModel::class.java]
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.counter.observe(viewLifecycleOwner) { currentValue: Int? ->
            binding.tvCounterText.text = currentValue.toString()
        }


        binding.btnIncrementButton.setOnClickListener {
            viewModel.increment()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}