package me.androidbox.presentation.navigation

sealed class Screen(val route: String) {

    object LoginScreen : Screen(route = "login_screen")

    object RegisterScreen : Screen(route = "register_screen")

    object AgendaScreen : Screen(route = "agenda_screen") {}

    object EditScreen : Screen(route = "edit_screen/{content}")

    object EventScreen : Screen(route = "event_screen")
}
