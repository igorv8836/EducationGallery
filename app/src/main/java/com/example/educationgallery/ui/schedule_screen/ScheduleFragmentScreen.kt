package com.example.educationgallery.ui.schedule_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.educationgallery.ui.schedule_screen.components.ScheduleList
import com.example.educationgallery.ui.schedule_screen.components.TopCalendarBar
import com.example.educationgallery.viewmodels.ScheduleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScheduleFragmentScreen(viewModel: ScheduleViewModel) {
    val weekPageState = rememberPagerState()
    val mainPageState = rememberPagerState()
    val currWeekPageIndex = remember { mutableIntStateOf(0) }
    val isOddWeek = remember { mutableStateOf(true) }
    val isSelectedInOddWeek = remember { mutableStateOf(true) }
    val tabs = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")


    LaunchedEffect(isOddWeek.value) {
        val pageNum = if (isOddWeek.value) 0 else 1
        if (pageNum != weekPageState.currentPage) {
            weekPageState.animateScrollToPage(pageNum)
        }
    }

    LaunchedEffect(weekPageState.currentPage) {
        val newIsOddWeek = weekPageState.currentPage == 0
        if (newIsOddWeek != isOddWeek.value) {
            isOddWeek.value = newIsOddWeek
        }
    }

    LaunchedEffect(currWeekPageIndex.intValue, isOddWeek.value) {
        val pageNum =
            if (isSelectedInOddWeek.value) currWeekPageIndex.intValue else currWeekPageIndex.intValue + 7
        if (pageNum != mainPageState.currentPage) {
            mainPageState.scrollToPage(pageNum)
        }
    }

    LaunchedEffect(mainPageState.currentPage) {
        val indx = mainPageState.currentPage
        val expectedPage = if (indx < 7) 0 else 1
        if (indx / 7 != (currWeekPageIndex.intValue + (7 * if (isSelectedInOddWeek.value) 0 else 1)) / 7) isSelectedInOddWeek.value =
            !isSelectedInOddWeek.value
        if (expectedPage != weekPageState.currentPage) {
            isOddWeek.value = !isOddWeek.value
        }
        val newCurrWeekPageIndex = indx % 7
        if (newCurrWeekPageIndex != currWeekPageIndex.intValue) {
            currWeekPageIndex.intValue = newCurrWeekPageIndex
        }
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
                isOddWeek = isOddWeek,
                weekPageState = weekPageState,
                currWeekPageIndex = currWeekPageIndex,
                isSelectedInOddWeek = isSelectedInOddWeek,
                tabs = tabs
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
