package com.example.educationgallery.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.educationgallery.R
import com.example.educationgallery.ui.button_navigation.BottomNavigation
import com.example.educationgallery.ui.button_navigation.NavGraph
import com.example.educationgallery.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainActivityViewModel
    private var navController: NavController? = null


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setContent {
            MaterialTheme {
                var showSyncDialog by remember { mutableStateOf(false) }
                var groupText by remember { mutableStateOf("") }
                val navController = rememberNavController()

                if (showSyncDialog) {
                    AlertDialog(
                        onDismissRequest = { showSyncDialog = false },
                        title = { Text("Синхронизация расписания") },
                        text = {
                            Column {
                                Text(
                                    "Вы уверены, что хотите синхронизировать расписание?",
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                TextField(
                                    value = groupText,
                                    onValueChange = { groupText = it },
                                    label = { Text("Введите группу") },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                        },
                        confirmButton = {
                            TextButton(onClick = {
                                showSyncDialog = false
                                viewModel.syncSchedule(groupText)
                            }) {
                                Text("Синхронизировать")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSyncDialog = false }) {
                                Text("Отмена")
                            }
                        },
                    )
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            title = {
                                Text(stringResource(id = R.string.app_name))
                            },
                            actions = {
                                IconButton(onClick = { showSyncDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Filled.Sync,
                                        tint = Color.White,
                                        contentDescription = "Sync icon"
                                    )
                                }
                            },
                        )
                    },
                    bottomBar = { BottomNavigation(navController = navController) }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        NavGraph(navHostController = navController)
                    }
                }
            }
        }
        checkPermission()
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