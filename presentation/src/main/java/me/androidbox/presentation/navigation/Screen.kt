package me.androidbox.presentation.navigation

sealed interface Screen {
    val route: String
}

object LoginScreen : Screen {
    override val route: String = "login_screen"
}

object RegisterScreen : Screen {
    override val route: String = "register_screen"
}
