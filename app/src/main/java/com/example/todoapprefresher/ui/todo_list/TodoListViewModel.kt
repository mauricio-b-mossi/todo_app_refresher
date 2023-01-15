package com.example.todoapprefresher.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapprefresher.data.Todo
import com.example.todoapprefresher.data.TodoRepository
import com.example.todoapprefresher.util.Route
import com.example.todoapprefresher.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel(){

    val todos = repository.getTodos()

    var deletedTodo : Todo? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event : TodoListEvent){
        when(event){
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnDeleteTodo -> {
                viewModelScope.launch {
                    try {
                        repository.deleteTodo(event.todo)
                        deletedTodo = event.todo
                        _uiEvent.send(UiEvent.ShowSnackbar(message = "Deleted Todo", action = "Undo"))
                    } catch (e : Exception) {
                        _uiEvent.send(UiEvent.ShowSnackbar(message = "Unable to Delete Todo"))
                    }
                }
            }
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Route.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnUndoDelete -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repository.insertTodo(it)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event : UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}