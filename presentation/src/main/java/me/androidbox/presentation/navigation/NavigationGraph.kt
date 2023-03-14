package me.androidbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.androidbox.presentation.login.screen.LoginScreen
import me.androidbox.presentation.login.screen.RegisterScreen
import me.androidbox.presentation.login.viewmodel.LoginViewModel
import me.androidbox.presentation.login.viewmodel.RegisterViewModel

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
            val loginViewModel: LoginViewModel = hiltViewModel()
            val loginState = loginViewModel.loginState.collectAsState()

            LoginScreen(
                loginState = loginState,
                onSignUpClicked = {
                    /* Signup clicked, navigate to register screen */
                    navHostController.navigate(route = Screen.RegisterScreen.route)
                },
                onLoginSuccess = { login ->
                    loginViewModel.saveCurrentUserDetails(login)
                    /* TODO Navigate to the agenda screen (not implemented yet) */
                },
                email = loginViewModel.email,
                onEmailChanged = { newEmail ->
                    loginViewModel.onEmailChanged(newEmail)
                },
                password = loginViewModel.password,
                onPasswordChanged = { newPassword ->
                    loginViewModel.onPasswordChanged(newPassword)
                },
                onPasswordVisibilityChanged = {
                    loginViewModel.onPasswordVisibilityChanged()
                },
                isPasswordVisible = loginViewModel.isPasswordVisible,
                onLoginUser = { email, password ->
                    loginViewModel.loginUser(email, password)
                }
            )
        }

        /* Register Screen */
        composable(
            route = Screen.RegisterScreen.route
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            val registerState = registerViewModel.registrationState.collectAsState()

            RegisterScreen(
                registrationState = registerState,
                onBackArrowClicked = {
                    /* Back arrow clicked, pop RegisterScreen of the backstack to get back to login screen */
                    navHostController.popBackStack()
                },
                onRegistrationSuccess = {
                    /* Registration Success */
                    navHostController.popBackStack()
                },
                username = registerViewModel.username.collectAsState().value,
                email = registerViewModel.email.collectAsState().value,
                password = registerViewModel.password.collectAsState().value,
                isPasswordVisible = registerViewModel.isPasswordVisible.collectAsState().value,
                onUsernameChanged = { newUsername ->
                    registerViewModel.onUsernameChanged(newUsername)
                },
                onEmailAddress = { newEmail ->
                    registerViewModel.onEmailAddress(newEmail)
                },
                onPasswordChanged = { newPassword ->
                    registerViewModel.onPasswordChanged(newPassword)
                },
                onPasswordVisibilityChanged = {
                    registerViewModel.onPasswordVisibilityChanged()
                },
                onRegisterUser = {
                    registerViewModel.registerUser()
                }
            )
        }
    }
}