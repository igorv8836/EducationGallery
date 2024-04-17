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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize


@Composable
fun CustomAlertDialogWithInputs(
    onDismissRequest: () -> Unit,
    onDeleteClick: () -> Unit,
    onSaveClick: (String, String, String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val types = listOf("ПР", "ЛК")
    var selectedType by remember { mutableStateOf(types.first()) }
    val times = listOf("9:00-10:30", "10:40-12:10", "12:40-14:10")
    var selectedTime by remember { mutableStateOf(times.first()) }

    var expandedType by remember { mutableStateOf(false) }
    var expandedTime by remember { mutableStateOf(false) }

    val suggestions = listOf(
        "Работа",
        "Личное",
        "Дом",
        "Учеба",
    )
    var expanded by remember { mutableStateOf(false) }
    val filteredSuggestions = suggestions.filter { it.contains(text, ignoreCase = true) }

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
                IconButton(onClick = { onDeleteClick() }) {
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
                    },
                    label = { Text("Введите текст") },
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
                                items(suggestions) {
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
                    options = types,
                    selectedOption = selectedType,
                    expanded = expandedType,
                    onExpandedChange = { expandedType = it },
                    onOptionSelected = { selectedType = it }
                )
                DropdownSelector(
                    label = "Время занятия",
                    options = times,
                    selectedOption = selectedTime,
                    expanded = expandedTime,
                    onExpandedChange = { expandedTime = it },
                    onOptionSelected = { selectedTime = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onSaveClick(text, selectedType, selectedTime) },
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

@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Box {
            TextButton(onClick = { onExpandedChange(true) }) {
                Text(selectedOption)
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onOptionSelected(option)
                            onExpandedChange(false)
                        },
                        text = {
                            Text(option)
                        }
                    )
                }
            }
        }
    }
}
