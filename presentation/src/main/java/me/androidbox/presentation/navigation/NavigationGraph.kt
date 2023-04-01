package me.androidbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.androidbox.presentation.agenda.screen.AgendaScreen
import me.androidbox.presentation.agenda.viewmodel.AgendaViewModel
import me.androidbox.presentation.edit.screen.ContentType
import me.androidbox.presentation.edit.screen.EditScreen
import me.androidbox.presentation.edit.screen.EditScreenEvent
import me.androidbox.presentation.edit.viewmodel.EditScreenViewModel
import me.androidbox.presentation.event.screen.EventScreen
import me.androidbox.presentation.event.screen.EventScreenEvent
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
                },
            onSelectedAgendaItem = {
                /* TODO The item in the dropdown menu should be an enum or a sealed class that will determine which item was clicked */
                navHostController.navigate(Screen.EventScreen.route)
            })
        }

        /* Event Detail Screen */
        composable(
            route = Screen.EventScreen.route
        ) {
            val eventViewModel: EventViewModel = hiltViewModel()
            val eventScreenState by eventViewModel.eventScreenState.collectAsStateWithLifecycle()
            val title = it.savedStateHandle.get<String>(ContentType.TITLE.name) ?: "New Event"
            val description = it.savedStateHandle.get<String>(ContentType.DESCRIPTION.name) ?: "New Description"

            LaunchedEffect(key1 = title, key2 = description) {
                eventViewModel.onEventScreenEvent(
                    EventScreenEvent.OnSaveTitleOrDescription(
                        title,
                        description
                    )
                )
            }

            EventScreen(
                eventScreenState = eventScreenState,
                eventScreenEvent = { eventScreenEvent ->
                    eventViewModel.onEventScreenEvent(eventScreenEvent)
                },
                onEditTitleClicked = { title ->
                    /** Navigate to the edit screen with the title of the agenda item */
                    navHostController.navigate(route = "${Screen.EditScreen.EDIT_SCREEN}/$title/${ContentType.TITLE}")
                },
                onEditDescriptionClicked = { description ->
                    /** Navigate to the edit screen with the title of the agenda item */
                    navHostController.navigate("${Screen.EditScreen.EDIT_SCREEN}/$description/${ContentType.DESCRIPTION}")
                }
            )
        }
        
        /* Edit Screen */
        composable(
            route = Screen.EditScreen.route,
            arguments = listOf(navArgument(Screen.EditScreen.CONTENT) {
                type = NavType.StringType
            }, navArgument(Screen.EditScreen.CONTENT_TYPE) {
                type = NavType.StringType
            })
        ) {
            val editScreenViewModel: EditScreenViewModel = hiltViewModel()
            val editScreenState by editScreenViewModel.editScreenState.collectAsStateWithLifecycle()
            val content = it.arguments?.getString(Screen.EditScreen.CONTENT) ?: ""

            /** Get the contentType that has been edited which could be either the Title or the Description
             *  If null then return the default title instead */
            val contentType = it.arguments?.getString(Screen.EditScreen.CONTENT_TYPE)?.let { contentType ->
                when(contentType) {
                    ContentType.TITLE.name -> ContentType.TITLE
                    ContentType.DESCRIPTION.name -> ContentType.DESCRIPTION
                    else -> { ContentType.TITLE}
                }
            } ?: ContentType.TITLE

            LaunchedEffect(key1 = content) {
                editScreenViewModel.onEditScreenEvent(
                    editScreenEvent = EditScreenEvent.OnContentChanged(content))
            }

            EditScreen(
                contentType = contentType,
                editScreenState = editScreenState,
                editScreenEvent = { editScreenEvent ->
                    editScreenViewModel.onEditScreenEvent(editScreenEvent)
                },
                onBackClicked = {
                    navHostController.popBackStack()
                },
                onSaveClicked = { content, contentType ->
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(contentType.name, content)
                    navHostController.popBackStack()
                }
            )
        }
    }
}