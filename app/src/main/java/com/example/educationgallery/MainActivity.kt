package com.example.educationgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.educationgallery.databinding.ActivityMainBinding
import com.example.educationgallery.viewmodels.MainActivityViewModel

/**
 * This is a documentation comment for a function.
 * It should provide a description of what the function does,
 * what parameters it accepts, and what it returns.
 *
 * @return The sum of x and y.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

    }
}