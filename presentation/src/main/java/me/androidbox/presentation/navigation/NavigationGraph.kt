package me.androidbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.androidbox.presentation.agenda.screen.AgendaScreen
import me.androidbox.presentation.agenda.viewmodel.AgendaViewModel
import me.androidbox.presentation.event.screen.EventScreen
import me.androidbox.presentation.event.viewmodel.EventViewModel
import me.androidbox.presentation.login.screen.LoginScreen
import me.androidbox.presentation.login.screen.RegisterScreen
import me.androidbox.presentation.login.viewmodel.LoginViewModel
import me.androidbox.presentation.login.viewmodel.RegisterViewModel

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {

        /* LoginScreen */
        composable(
            route = Screen.LoginScreen.route
        ) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            val loginScreenState
                    = loginViewModel.loginScreenState.collectAsStateWithLifecycle()

            LoginScreen(
                loginScreenEvent = { loginEvent ->
                    loginViewModel.onLoginEvent(loginEvent)
                },
                authenticationScreenState = loginScreenState,
                onSignUpClicked = {
                    /* Signup clicked, navigate to register screen */
                    navHostController.navigate(route = Screen.RegisterScreen.route)
                },
                onLoginSuccess = { authenticatedUser ->
                    /* TODO Pass the authenticatedUser as an argument */
                    navHostController.navigate(route = Screen.AgendaScreen.route)
                },
            )
        }

        /* Register Screen */
        composable(
            route = Screen.RegisterScreen.route
        ) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            val registerScreenState
                    = registerViewModel.registerScreenState.collectAsStateWithLifecycle()

            RegisterScreen(
                loginScreenEvent = { loginScreenEvent ->
                    registerViewModel.onRegistrationEvent(loginScreenEvent)
                },
                registerScreenState = registerScreenState,
                onBackArrowClicked = {
                    /* Back arrow clicked, pop RegisterScreen of the backstack to get back to login screen */
                    navHostController.popBackStack()
                },
                onRegistrationSuccess = {
                    /* Registration Success */
                    navHostController.popBackStack()
                },
            )
        }

        /* Agenda Screen */
        composable(
            route = Screen.AgendaScreen.route
        ) {
            val agendaViewModel: AgendaViewModel = hiltViewModel()
            val agendaScreenState by agendaViewModel.agendaScreenState.collectAsStateWithLifecycle()

            AgendaScreen(
                agendaScreenState = agendaScreenState,
                agendaScreenEvent = { agendaScreenEvent ->
                    agendaViewModel.onAgendaScreenEvent(agendaScreenEvent)
                })
        }

        /* Event Screen */
        composable(
            route = Screen.EditScreen.route
        ) {
            val eventViewModel: EventViewModel = hiltViewModel()
            val eventScreenState by eventViewModel.eventScreenState.collectAsStateWithLifecycle()

            EventScreen(
                eventScreenState = eventScreenState,
                eventScreenEvent = { eventScreenEvent ->
                    eventViewModel.onEventScreenEvent(eventScreenEvent)
                }
            )
        }
    }
}