package com.example.todoapprefresher.util

sealed class UiEvent {
    data class ShowSnackbar(val message : String, val action : String? = null) : UiEvent()
    data class Navigate(val route : String) : UiEvent()
    object PopBackSack : UiEvent()
}
