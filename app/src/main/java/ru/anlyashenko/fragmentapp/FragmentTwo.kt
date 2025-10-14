package ru.anlyashenko.fragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import ru.anlyashenko.fragmentapp.databinding.FragmentTwoBinding


class FragmentTwo : Fragment() {

//    companion object {
//        const val USER_KEY = "user_key"
//    }

    private var _binding: FragmentTwoBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentTwoBinding must not be null")

    private val args: FragmentTwoArgs by navArgs()

//    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        user = arguments?.getParcelable<User>(USER_KEY)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.tvDescFragment.text = user.toString()
        val receivedUser = args.user
        binding.tvDescFragment.text = receivedUser.toString()
    }
}