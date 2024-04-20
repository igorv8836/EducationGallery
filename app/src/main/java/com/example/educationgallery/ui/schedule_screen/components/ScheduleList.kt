package com.example.educationgallery.ui.schedule_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.educationgallery.R
import com.example.educationgallery.ui.models.LessonView
import com.example.educationgallery.ui.schedule_screen.CustomAlertDialogWithInputs
import com.example.educationgallery.viewmodels.ScheduleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScheduleList(
    viewModel: ScheduleViewModel,
    mainPageState: PagerState
) {
    val schedule = viewModel.lessons.collectAsState()
    var selectedLesson by remember { mutableStateOf<LessonView?>(null) }

    var showDialog by remember { mutableStateOf(false) }
    var isCreating by remember { mutableStateOf(false) }

    val currPageIndex = mainPageState.currentPage

    if (showDialog) {
        CustomAlertDialogWithInputs(viewModel, selectedLesson, isCreating) {
            showDialog = false
        }
    }

    HorizontalPager(
        count = 14, state = mainPageState, modifier = Modifier.fillMaxSize()
    ) {
        val interactionSource = remember { MutableInteractionSource() }
        val indication = rememberRipple(bounded = true)
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(interactionSource = interactionSource,
                        indication = indication,
                        onClick = {
                            isCreating = true
                            showDialog = true
                        }),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "add event",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 16.dp, bottom = 8.dp)
                        .size(32.dp)
                )
                Text(
                    text = "Добавить событие",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 8.dp, top = 16.dp, bottom = 8.dp)
                        .align(Alignment.CenterVertically),
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                val daySchedule = if (currPageIndex < 7) {
                    schedule.value?.oddWeek?.dayScheduleList?.get(currPageIndex % 7)?.lessonsList
                        ?: emptyList()
                } else {
                    schedule.value?.evenWeek?.dayScheduleList?.get(currPageIndex % 7)?.lessonsList
                        ?: emptyList()
                }
                items(daySchedule) {
                    LessonItem(it) {
                        selectedLesson = it
                        isCreating = false
                        showDialog = true
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }

    }
}