package com.example.educationgallery.ui.photo_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.educationgallery.R
import com.example.educationgallery.ui.models.SubjectFolderView

@Composable
fun SubjectFolderItem(folderView: SubjectFolderView, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }
    val ripple = rememberRipple(bounded = true)
    Surface(
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(interactionSource = interactionSource, indication = ripple, onClick = onClick)
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
                    text = folderView.name,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                )
            }
//
//            Text(
//                text = "10 шт",
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(end = 12.dp, bottom = 4.dp)
//            )
        }

    }
}