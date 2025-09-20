package ru.anlyashenko.fragmentapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.anlyashenko.fragmentapp.databinding.BottomSheetOptionsBinding

class OptionsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: BottomSheetOptionsBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for BottomSheetOptionsBinding must not be null")

    lateinit var listener: OptionsListener

    interface OptionsListener {
        fun onOptionClicked(optionId: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = parentFragment as? OptionsListener
            ?: throw ClassCastException("Parent Fragment must implement OptionListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvOption1.setOnClickListener {
            listener.onOptionClicked("option1")
            dismiss()
        }

        binding.tvOption2.setOnClickListener {
            listener.onOptionClicked("option2")
            dismiss()
        }

        binding.tvOption3.setOnClickListener {
            listener.onOptionClicked("option3")
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}