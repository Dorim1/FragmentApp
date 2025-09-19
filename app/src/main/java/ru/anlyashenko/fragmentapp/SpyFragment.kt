package ru.anlyashenko.fragmentapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.anlyashenko.fragmentapp.databinding.FragmentSpyBinding
import kotlin.math.log

class SpyFragment : Fragment() {

    companion object {
        const val TAG = "LOGTAG"
    }

    private var _binding: FragmentSpyBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for FragmentSpyBinding must not be null")


    // Фрагмент прикрепляется к Активити. В этот момент он получает доступ к контексту
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "Lifecycle: onAttach() called")
    }

    // Фрагмент создаётся. Здесь инициализируются данные, которые не относятся к UI
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Lifecycle: onCreate() called")
    }

    // Здесь фрагмент должен создать и вернуть иерархию своих View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Lifecycle: onCreateView() called")
        _binding = FragmentSpyBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Основная логика работы с View фрагмента
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Lifecycle: onViewCreated() called")
    }

    // Фрагмент становится видимым для взаимодействия, синхронно с Активити
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Lifecycle: onStart() called")
    }

    // Фрагмент становится видимым для взаимодействия, синхронно с Активити
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Lifecycle: onResume() called")
    }

    // Фрагмент уходит с переднего плана
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Lifecycle: onPause() called")
    }

    // Фрагмент уходит с переднего плана
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Lifecycle: onStop() called")
    }

    // Иерархия View уничтожается. Здесь нужно обнулять все ссылки на View, чтобы избежать утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "Lifecycle: onDestroyView() called")
        _binding = null
    }

    // Фрагмент полностью уничтожается
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Lifecycle: onDestroy() called")
    }

    // Фрагмент открепляется от Активити
    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "Lifecycle: onDetach() called")
    }


}