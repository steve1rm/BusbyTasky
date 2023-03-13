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
        startDestination = Screen.LoginScreen.route
    ) {

        /* LoginScreen */
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(
                onSignUpClicked = {
                    /* Signup clicked, navigate to register screen */
                    navHostController.navigate(route = Screen.RegisterScreen.route)
                },
                onLoginSuccess = {
                    /* TODO Navigate to the agenda screen (not implemented yet) */
                })
        }

        /* Register Screen */
        composable(
            route = Screen.RegisterScreen.route
        ) {
            RegisterScreen(
                onBackArrowClicked = {
                /* Back arrow clicked, pop RegisterScreen of the backstack to get back to login screen */
                navHostController.popBackStack()
            },
            onRegistrationSuccess = {
                /* Registration Success */
                navHostController.popBackStack()
            })
        }
    }
}