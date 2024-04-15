package com.example.educationgallery.ui.fragments

import android.content.Context
import android.database.Cursor
import android.media.ExifInterface
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.educationgallery.R
import java.io.File
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
                    PreviewPhotoFragment()
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

@Preview(showBackground = true)
@Composable
fun PreviewPhotoFragment() {
    MaterialTheme {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(100) {
                FolderItem()
            }
        }
    }
}

@Composable
fun FolderItem() {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(bounded = true)
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(interactionSource = interactionSource, indication = ripple, onClick = {})
    ) {
        Box {
            Row(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.folder),
                    contentDescription = "folder",
                    colorFilter = ColorFilter.tint(Color.Green),
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(48.dp)
                )

                Text(
                    text = "Folder",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                )
            }

            Text(
                text = "10 шт",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp)
            )
        }

    }
}