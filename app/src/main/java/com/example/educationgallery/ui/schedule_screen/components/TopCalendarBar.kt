package com.example.educationgallery.ui.schedule_screen.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopCalendarBar(
    currDayIndex: Int,
    weekPageState: PagerState,
    tabs: List<String>,
    clickToChangeWeek: (weekIndex: Int) -> Unit,
    clickToChangeDay: (index: Int) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 36.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Previous",
                tint = if (weekPageState.currentPage == 1) MaterialTheme.colorScheme.primary else Color.Gray,
                modifier = Modifier
                    .clickable(enabled = weekPageState.currentPage == 1) {
                        clickToChangeWeek(0)
                    }
                    .clip(RoundedCornerShape(50)))
            Text(
                text = if (weekPageState.currentPage == 1) "Нечетная неделя" else "Четная неделя",
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                tint = if (weekPageState.currentPage == 0) MaterialTheme.colorScheme.primary else Color.Gray,
                contentDescription = "Next",
                modifier = Modifier
                    .clickable(enabled = weekPageState.currentPage == 0) {
                        clickToChangeWeek(1)
                    }
                    .clip(RoundedCornerShape(50)))
        }
        HorizontalPager(
            count = 2, state = weekPageState, modifier = Modifier.fillMaxWidth()
        ) { weekIndex ->
            TabRow(selectedTabIndex = currDayIndex % 7, indicator = {}, divider = {}) {
                tabs.forEachIndexed { index, s ->
                    val isSelected = (currDayIndex % 7 == index) && (currDayIndex / 7 == weekIndex)
                    val backgroundColor = animateColorAsState(
                        targetValue = if (isSelected) Color.LightGray else Color.Transparent,
                        label = ""
                    )
                    Tab(selected = isSelected, onClick = {
                        clickToChangeDay(weekIndex * 7 + index)
                    }, interactionSource = object : MutableInteractionSource {
                        override val interactions: Flow<Interaction> = emptyFlow()
                        override suspend fun emit(interaction: Interaction) {}
                        override fun tryEmit(interaction: Interaction) = true
                    }, modifier = Modifier.wrapContentSize()
                    ) {
                        Text(
                            text = s,
                            modifier = Modifier
                                .clip(RoundedCornerShape(percent = 100))
                                .background(backgroundColor.value)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }

}