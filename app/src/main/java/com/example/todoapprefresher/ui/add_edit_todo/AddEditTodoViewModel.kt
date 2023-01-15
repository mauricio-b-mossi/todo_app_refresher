package com.example.todoapprefresher.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapprefresher.data.Todo
import com.example.todoapprefresher.data.TodoRepository
import com.example.todoapprefresher.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<Int>("todoId")?.let { todoId ->
            if (todoId == -1) return@let
            viewModelScope.launch {
                try {
                    repository.getTodoById(todoId)?.let { fetchedTodo ->
                        todo = fetchedTodo
                    }
                } catch (e: Exception) {
                    _uiEvent.send(UiEvent.ShowSnackbar(message = "Unable to get Todo"))
                }
            }
        }
    }

    var todo by mutableStateOf<Todo?>(null)

    var title by mutableStateOf<String>("")
        private set

    var description by mutableStateOf<String>("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditTodoEvent) {
        when (event) {
            is AddEditTodoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditTodoEvent.OnSubmit -> {
                viewModelScope.launch {
                    try {
                        repository.insertTodo(
                            Todo(
                                title = title,
                                description = description,
                                id = todo?.id
                            )
                        )
                        _uiEvent.send(UiEvent.PopBackSack)

                    } catch (e : Exception) {
                        _uiEvent.send(UiEvent.ShowSnackbar(message = "Unable to Insert Todo"))
                    }
                }
            }
        }
    }
}