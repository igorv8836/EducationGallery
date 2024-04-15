package com.example.educationgallery.ui.fragments

import android.content.Context
import android.database.Cursor
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.educationgallery.ui.components.PhotoFragmentScreen
import java.io.IOException


class PhotoFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    PhotoFragmentScreen()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val a = getAllPhotos(requireContext())
        for (i in a){
            getPhotoMetadata(requireContext(), i)
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