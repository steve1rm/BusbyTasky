package me.androidbox.presentation.navigation

sealed class Screen(val route: String) {

    object LoginScreen : Screen(route = "login_screen")

    object RegisterScreen : Screen(route = "register_screen")
}
