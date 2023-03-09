package me.androidbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.androidbox.presentation.login.screen.LoginScreen
import me.androidbox.presentation.login.screen.RegisterScreen

@Composable
fun NavigationGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = LoginScreen.route
    ) {

        /* LoginScreen */
        composable(
            route = LoginScreen.route
        ) {
            LoginScreen {
                /* Signup clicked, navigate to register screen */
                navHostController.navigate(route = RegisterScreen.route)
            }
        }

        /* Register Screen */
        composable(
            route = RegisterScreen.route
        ) {
            RegisterScreen {
                /* Back arrow clicked, pop RegisterScreen of the backstack to get back to login screen */
                navHostController.popBackStack()
            }
        }
    }
}