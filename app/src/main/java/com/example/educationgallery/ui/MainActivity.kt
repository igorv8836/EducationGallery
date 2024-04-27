package com.example.educationgallery.ui

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.ExifInterface
import android.media.MediaMetadata
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.educationgallery.R
import com.example.educationgallery.model.LessonTime
import com.example.educationgallery.ui.navigation.button_navigation.BottomNavigation
import com.example.educationgallery.ui.navigation.button_navigation.NavGraph
import com.example.educationgallery.viewmodels.MainActivityViewModel
import java.io.IOException
import java.util.Date

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

fun getPhotoMetadata(context: Context, photoUri: Uri) {
    try {
        context.contentResolver.openInputStream(photoUri)?.use { inputStream ->
            val exifInterface = ExifInterface(inputStream)
            val dateTime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME)
            val a = 12
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}
fun getDateTaken(context: Context, photoUri: Uri): Date? {
    val resolver = context.contentResolver
    val cursor = resolver.query(
        photoUri,
        arrayOf(MediaStore.Images.Media.DATE_TAKEN),
        null,
        null,
        null
    )
    if (cursor != null && cursor.moveToFirst()) {
        val dateTaken = cursor.getLong(0)
        cursor.close()
        return Date(dateTaken)
    }
    return null
}
fun getAllPhotos(context: Context): List<Uri> {
    val uriExternal: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val cursor: Cursor?
    val columnIndexID: Int
    val listOfAllImages: MutableList<Uri> = mutableListOf()
    val projection = arrayOf(MediaStore.Images.Media._ID)

    var imageUri: Uri
    val orderBy = MediaStore.Images.Media.DATE_TAKEN

    cursor = context.contentResolver.query(
        uriExternal, projection, null, null, "$orderBy DESC"
    )

    if (cursor != null) {
        columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        while (cursor.moveToNext()) {
            imageUri = Uri.withAppendedPath(uriExternal, "" + cursor.getLong(columnIndexID))
            listOfAllImages.add(imageUri)
        }
        cursor.close()
    }

    return listOfAllImages
}