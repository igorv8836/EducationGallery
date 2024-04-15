package com.example.educationgallery.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.educationgallery.R
import com.example.educationgallery.databinding.ActivityMainBinding
import com.example.educationgallery.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        navController = (supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment)
            .navController

        binding.bottomMenu.setOnItemSelectedListener{
            when(it.itemId){
                R.id.menu_schedule -> {
                    navController.navigate(R.id.action_to_scheduleFragment)
                }
                R.id.menu_gallery -> {
                    navController.navigate(R.id.action_to_photoFragment)
                }
                R.id.menu_settings -> {

                }
                else -> {}
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun checkPermission() {
        val permission = when {
            Build.VERSION.SDK_INT >= 33 -> android.Manifest.permission.READ_MEDIA_IMAGES
            else -> android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(permission), 1)
        }
    }


}