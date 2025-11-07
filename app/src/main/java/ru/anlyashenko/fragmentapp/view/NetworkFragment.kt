package ru.anlyashenko.fragmentapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anlyashenko.fragmentapp.databinding.FragmentNetworkBinding
import ru.anlyashenko.fragmentapp.viewModel.PostViewModel
import ru.anlyashenko.fragmentapp.viewModel.UserViewModel

class NetworkFragment : Fragment() {

    private var _binding: FragmentNetworkBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentNetworkBinding must not be null")

    private val viewModel: PostViewModel by viewModels()
    private val viewModel2: UserViewModel by viewModels()

    val adapter = PostAdapter()


//    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNetworkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnFetchOkhttp.setOnClickListener { viewModel.loadPost() }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            }  else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.loadPost()

        binding.postsRecyclerView.adapter = adapter
        binding.postsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        viewModel.posts.observe(viewLifecycleOwner) { postList ->
            adapter.submitList(postList)
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), "Ошибка: $error", Toast.LENGTH_SHORT).show()
        }


        binding.btnFetchOkhttpUser.setOnClickListener { viewModel2.loadUser() }
        viewModel2.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel2.displayText.observe(viewLifecycleOwner) { user ->
            binding.tvResult.text = user.toString()
        }

        viewModel2.error.observe(viewLifecycleOwner) { error ->
            binding.tvResult.text = error
        }

    }

//    private fun startRequest() {
//        binding.progressBar.visibility = View.VISIBLE
//        val url = "https://jsonplaceholder.typicode.com/posts/1"
//        val request = Request.Builder()
//            .url(url)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                view?.post {
//                    binding.progressBar.visibility = View.GONE
//                    binding.tvResult.text = "Ошибка: ${e.message}"
//                }
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                response.use {
//                    val responseBody = it.body.string()
//                    view?.post {
//                        binding.progressBar.visibility = View.GONE
//                        if (it.isSuccessful) {
//                            binding.tvResult.text = responseBody
//                        } else {
//                            binding.tvResult.text = "Ошибка сервера: ${it.code}"
//                        }
//                    }
//                }
//            }
//        })
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}