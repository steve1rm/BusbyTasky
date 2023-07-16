package me.androidbox.presentation.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import me.androidbox.presentation.login.screen.LoginScreen
import me.androidbox.presentation.login.screen.RegisterScreen
import me.androidbox.presentation.login.viewmodel.LoginViewModel
import me.androidbox.presentation.login.viewmodel.RegisterViewModel

fun NavGraphBuilder.authenticationGraph(
    navHostController: NavHostController
) {
    navigation(
        startDestination = Screen.LoginScreen.route,
        route = Screen.Authentication.route
    ) {
        /** LoginScreen */
        composable(
            route = Screen.LoginScreen.route
        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val authenticationUserState
                    by loginViewModel.authenticateUserState.collectAsStateWithLifecycle()
            val validateCredentialsState
                    by loginViewModel.validateCredentialsState.collectAsStateWithLifecycle()

            LoginScreen(
                loginScreenEvent = { loginEvent ->
                    loginViewModel.onLoginEvent(loginEvent)
                },
                authenticationUserState = authenticationUserState,
                validateCredentialsState = validateCredentialsState,
                onSignUpClicked = {
                    /* Signup clicked, navigate to register screen */
                    navHostController.navigate(route = Screen.RegisterScreen.route)
                },
                onLoginSuccess = { authenticatedUser ->
                    /* TODO Pass the authenticatedUser as an argument */
                    navHostController.navigate(route = Screen.AgendaScreen.route)
                }
            )
        }

        /* Register Screen */
        composable(
            route = Screen.RegisterScreen.route
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            val registerScreenState
                    by registerViewModel.registerScreenState.collectAsStateWithLifecycle()
            val validateCredentialState by registerViewModel.validateCredentials.collectAsStateWithLifecycle()

            RegisterScreen(
                loginScreenEvent = { loginScreenEvent ->
                    registerViewModel.onRegistrationEvent(loginScreenEvent)
                },
                registerScreenState = registerScreenState,
                validateCredentialsState = validateCredentialState,
                onBackArrowClicked = {
                    /* Back arrow clicked, pop RegisterScreen of the backstack to get back to login screen */
                    navHostController.popBackStack()
                },
                onRegistrationSuccess = {
                    /* Registration Success */
                    navHostController.popBackStack()
                }
            )
        }
    }
}