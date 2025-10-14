package ru.anlyashenko.fragmentapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.anlyashenko.fragmentapp.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity(){

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        // Эта строка связывает Toolbar с NavController и DrawerLayout.
        // Она отвечает за иконку-"гамбургер" и стрелку "назад".
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        // Эта строка связывает нижнее меню с NavController.
        binding.bottomNavMenu.setupWithNavController(navController)

        // Эта строка связывает боковое меню с NavController
        binding.navigationView.setupWithNavController(navController)
    }
}