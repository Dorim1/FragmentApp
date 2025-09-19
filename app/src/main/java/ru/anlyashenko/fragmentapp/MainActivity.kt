package ru.anlyashenko.fragmentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.anlyashenko.fragmentapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAddFragment.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, SpyFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnOpenFragment2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, CounterFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnDeleteFragment.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.FragmentContainer)
            if (fragment != null) {
                supportFragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit()
            }
        }

        binding.btnOpenActivity2.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}