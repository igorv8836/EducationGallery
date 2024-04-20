package com.example.educationgallery.ui.schedule_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.educationgallery.ui.models.LessonView
import com.example.educationgallery.viewmodels.ScheduleViewModel


@Composable
fun CustomAlertDialogWithInputs(
    viewModel: ScheduleViewModel,
    lessonView: LessonView?,
    onDismissRequest: () -> Unit
) {
    var text by remember { mutableStateOf(lessonView?.name ?: "") }
    val types = viewModel.lessonsTypes.collectAsState()
    var selectedType by remember { mutableStateOf(lessonView?.lessonType ?: types.value.first()) }
    val times = viewModel.lessonsTimes.collectAsState()
    var selectedTime by remember { mutableStateOf(lessonView?.time ?: times.value.first()) }

    var expandedType by remember { mutableStateOf(false) }
    var expandedTime by remember { mutableStateOf(false) }

    val suggestions = viewModel.filteredScheduleName.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    viewModel.deleteLesson()
                    onDismissRequest()
                    TODO("Передать данные")
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
        text = {
            Column {
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        expanded = it.isNotEmpty()
                        viewModel.getFilteredScheduleName(it)
                    },
                    label = { Text("Введите название") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size.toSize()
                        },
                    trailingIcon = {
                        if (text.isNotEmpty()) {
                            IconButton(onClick = {
                                text = ""
                                expanded = false
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear"
                                )
                            }
                        }
                    },
                    keyboardActions = KeyboardActions(onDone = { }),
                    singleLine = true
                )

                if (expanded) {
                    Box {
                        Card(
                            modifier = Modifier
                                .width(textFieldSize.width.dp),
                            elevation = 15.dp,
                            shape = RoundedCornerShape(10.dp)
                        ) {

                            LazyColumn(
                                modifier = Modifier.heightIn(max = 150.dp),
                            ) {
                                items(suggestions.value) {
                                    CategoryItems(title = it) { title ->
                                        text = title
                                        expanded = false
                                    }
                                }
                            }

                        }
                    }
                }

                DropdownSelector(
                    label = "Тип занятия",
                    options = types.value,
                    selectedOption = selectedType,
                    expanded = expandedType,
                    onExpandedChange = { expandedType = it },
                    onOptionSelected = { selectedType = it }
                )
                DropdownSelector(
                    label = "Время занятия",
                    options = times.value,
                    selectedOption = selectedTime,
                    expanded = expandedTime,
                    onExpandedChange = { expandedTime = it },
                    onOptionSelected = { selectedTime = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.changeSchedule()
                    onDismissRequest()
                    TODO("Передать данные")
                },
            ) {
                Text("Сохранить", color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = { onDismissRequest() },
            ) {
                Text("Отмена", color = MaterialTheme.colorScheme.primary)
            }
        }
    )
}