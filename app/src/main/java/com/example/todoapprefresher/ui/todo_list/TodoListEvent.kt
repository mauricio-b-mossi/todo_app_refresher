package com.example.todoapprefresher.ui.todo_list

import com.example.todoapprefresher.data.Todo

sealed class TodoListEvent{
    data class OnTodoClick(val todo : Todo) : TodoListEvent()
    data class OnDeleteTodo(val todo : Todo) : TodoListEvent()
    object OnUndoDelete : TodoListEvent()
    object OnAddTodoClick : TodoListEvent()
}
