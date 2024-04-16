package com.example.educationgallery.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.educationgallery.R
import com.example.educationgallery.viewmodels.ScheduleViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScheduleFragmentScreen(viewModel: ScheduleViewModel) {
    val weekPageState = rememberPagerState()
    val mainPageState = rememberPagerState()
    val currWeekPageIndex = remember { mutableIntStateOf(0) }
//    val currMainPageIndex = remember { mutableIntStateOf(0) }
    val isOddWeek = remember { mutableStateOf(true) }
    val tabs = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    LaunchedEffect(weekPageState.currentPage) {
        isOddWeek.value = weekPageState.currentPage == 0
        currWeekPageIndex.intValue = 0
    }

    LaunchedEffect(mainPageState.currentPage) {
        val indx = mainPageState.currentPage
        isOddWeek.value = indx < 7
        weekPageState.animateScrollToPage(if (indx < 7) 0 else 1)
        currWeekPageIndex.intValue = indx % 7
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 36.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Previous",
                tint = if (!isOddWeek.value) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier
                    .clickable(enabled = !isOddWeek.value) {
                        isOddWeek.value = !isOddWeek.value
                    }
                    .clip(RoundedCornerShape(50)))
            Text(
                text = if (isOddWeek.value) "Нечетная неделя" else "Четная неделя",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                tint = if (isOddWeek.value) MaterialTheme.colorScheme.primary else Color.Gray,
                contentDescription = "Next",
                modifier = Modifier
                    .clickable(enabled = isOddWeek.value) {
                        isOddWeek.value = !isOddWeek.value
                    }
                    .clip(RoundedCornerShape(50)))
        }
        HorizontalPager(
            count = 2,
            state = weekPageState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            TabRow(selectedTabIndex = currWeekPageIndex.intValue, indicator = {}) {
                tabs.forEachIndexed { index, s ->
                    val isSelected = currWeekPageIndex.intValue == index
                    val backgroundColor = animateColorAsState(
                        targetValue = if (isSelected) Color.LightGray else Color.Transparent,
                        label = ""
                    )
                    Tab(
                        selected = isSelected,
                        onClick = { currWeekPageIndex.intValue = index },
                        text = {
                            Text(
                                text = s,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(percent = 100))
                                    .background(backgroundColor.value)
                                    .padding(4.dp)
                            )
                        },
                        interactionSource = object : MutableInteractionSource {
                            override val interactions: Flow<Interaction> = emptyFlow()
                            override suspend fun emit(interaction: Interaction) {}
                            override fun tryEmit(interaction: Interaction) = true
                        })
                }
            }
        }


        HorizontalPager(
            count = 14,
            state = mainPageState,
            modifier = Modifier.fillMaxSize()
        ) {
            val interactionSource = remember { MutableInteractionSource() }
            val indication = rememberRipple(bounded = true)
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable(
                            interactionSource = interactionSource,
                            indication = indication,
                            onClick = { }
                        ),
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
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(10) {
                        HorizontalDivider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))
                        Text(text = "fadslkj")
                    }
                }
            }

        }
    }
}

@Composable
fun EventItem(){
    Column {
        Row {
            Text(text = "12:00")
            Text(text = "Событие")
        }
        Text(text = "Описание события")
    }
}
