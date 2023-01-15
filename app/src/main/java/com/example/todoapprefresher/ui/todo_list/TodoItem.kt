package com.example.todoapprefresher.ui.todo_list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapprefresher.data.Todo

@Composable
fun TodoItem(modifier: Modifier = Modifier, todo: Todo, onEvent: (TodoListEvent) -> Unit) {
    Card {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(text = todo.title)
                if (todo.description.isNotBlank()){
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = todo.description)
                }
            }
            IconButton(onClick = {onEvent(TodoListEvent.OnDeleteTodo(todo))}) {
               Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}