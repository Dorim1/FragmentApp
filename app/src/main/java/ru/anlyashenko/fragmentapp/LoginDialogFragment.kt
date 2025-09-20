package ru.anlyashenko.fragmentapp

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.anlyashenko.fragmentapp.databinding.DialogAddNoteBinding

class LoginDialogFragment : DialogFragment() {

    lateinit var listener: LoginListener

    private var _binding: DialogAddNoteBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Ошибка")

    interface LoginListener {
        fun onLoginClicked(login: String, password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? LoginListener
            ?: throw ClassCastException("Parent fragment must implement LoginListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.RoundedDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.9).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnSave.setOnClickListener {
            val login = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            listener.onLoginClicked(login, password)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}