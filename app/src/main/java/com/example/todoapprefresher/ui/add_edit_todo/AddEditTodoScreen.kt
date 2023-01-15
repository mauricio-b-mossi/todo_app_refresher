package com.example.todoapprefresher.ui.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapprefresher.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditTodoScreen(
    viewModel: AddEditTodoViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message, event.action)
                }
                is UiEvent.PopBackSack -> {
                    onPopBackStack()
                }
            }
        }
    }

    Scaffold(scaffoldState = scaffoldState, floatingActionButton = {
        IconButton(onClick = { viewModel.onEvent(AddEditTodoEvent.OnSubmit) }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = "Check")
        }
    }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.title,
                onValueChange = { viewModel.onEvent(AddEditTodoEvent.OnTitleChange(it)) },
                placeholder = {
                    Text(
                        text = "Title"
                    )
                })
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = viewModel.description,
                onValueChange = { viewModel.onEvent(AddEditTodoEvent.OnDescriptionChange(it)) },
                placeholder = {
                    Text(
                        text = "Description"
                    )
                })
        }
    }
}