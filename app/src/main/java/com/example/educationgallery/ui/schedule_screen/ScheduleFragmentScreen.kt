package com.example.educationgallery.ui.schedule_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.educationgallery.ui.schedule_screen.components.ScheduleList
import com.example.educationgallery.ui.schedule_screen.components.TopCalendarBar
import com.example.educationgallery.viewmodels.ScheduleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScheduleFragmentScreen(viewModel: ScheduleViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val weekPageState = rememberPagerState()
    val mainPageState = rememberPagerState()
    val tabs = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")


    LaunchedEffect(mainPageState.currentPage) {
        weekPageState.animateScrollToPage(mainPageState.currentPage / 7)
    }


    Column {
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(bottom = 16.dp)
        ) {
            TopCalendarBar(
                currDayIndex = mainPageState.currentPage,
                weekPageState = weekPageState,
                tabs = tabs,
                clickToChangeWeek = {
                    coroutineScope.launch {
                        weekPageState.animateScrollToPage(it)
                    }
                },
                clickToChangeDay = {
                    coroutineScope.launch {
                        mainPageState.animateScrollToPage(it)
                    }
                }
            )
        }
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp)
        ) {
            ScheduleList(viewModel = viewModel, mainPageState = mainPageState)
        }


    }
}
