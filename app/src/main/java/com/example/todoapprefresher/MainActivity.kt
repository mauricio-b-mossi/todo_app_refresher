package com.example.todoapprefresher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapprefresher.ui.add_edit_todo.AddEditTodoScreen
import com.example.todoapprefresher.ui.theme.TodoAppRefresherTheme
import com.example.todoapprefresher.ui.todo_list.TodoListScreen
import com.example.todoapprefresher.util.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoAppRefresherTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Route.TODO_LIST) {
                    composable(route = Route.TODO_LIST) {
                        TodoListScreen(onNavigate = { navController.navigate(it.route) })
                    }
                    composable(route = Route.ADD_EDIT_TODO + "?todoId={todoId}", arguments = listOf(
                        navArgument(name = "todoId"){
                            this.type = NavType.IntType
                            this.defaultValue = -1
                        }
                    )) {
                        AddEditTodoScreen(onPopBackStack = {navController.popBackStack()})
                    }
                }
            }
        }
    }
}