package ru.anlyashenko.fragmentapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.squareup.picasso.Picasso
import ru.anlyashenko.fragmentapp.R
import ru.anlyashenko.fragmentapp.databinding.FragmentAuthenticationBinding
import ru.anlyashenko.fragmentapp.viewModel.PostViewModel

class AuthenticationFragment : Fragment() {
    private var _binding: FragmentAuthenticationBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentAuthenticationBinding must not be null")

    private val viewModel: PostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthenticationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            val username = binding.etInputUsername.text.toString()
            val password = binding.etInputPassword.text.toString()
            viewModel.signIn(username, password)
        }

        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvFirstNameUser.text = user.firstName
            binding.tvLastNameUser.text = user.lastName
            Picasso.get().load(user.image).into(binding.ivUser)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}