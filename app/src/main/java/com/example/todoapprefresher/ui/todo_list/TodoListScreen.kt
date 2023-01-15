package com.example.todoapprefresher.ui.todo_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapprefresher.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    viewModel: TodoListViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {

    val todos = viewModel.todos.collectAsState(initial = emptyList())

    val rememberScaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }
                is UiEvent.ShowSnackbar -> {
                    val result =
                        rememberScaffoldState.snackbarHostState.showSnackbar(
                            event.message,
                            event.action
                        )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnUndoDelete)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(scaffoldState = rememberScaffoldState, floatingActionButton = {
        IconButton(onClick = { viewModel.onEvent(TodoListEvent.OnAddTodoClick) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }) {
        LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
            items(todos.value){ todo ->
                TodoItem(todo = todo, onEvent = viewModel::onEvent)
            }
        }
    }
}