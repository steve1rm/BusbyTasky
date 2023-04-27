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
import androidx.navigation.navDeepLink
import me.androidbox.presentation.agenda.constant.AgendaMenuActionType
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.domain.constant.AgendaDeepLinks
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
import me.androidbox.presentation.navigation.Screen.EditScreen.CONTENT
import me.androidbox.presentation.navigation.Screen.EditScreen.CONTENT_TYPE
import me.androidbox.presentation.navigation.Screen.EventScreen.EVENT_ID
import me.androidbox.presentation.navigation.Screen.EventScreen.MENU_ACTION_TYPE

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

            /** FIXME There is an issue as insertion, deletion should automatically trigger collectLatest flow */
            LaunchedEffect(key1 = agendaScreenState.selectedDate) {
                agendaViewModel.fetchAgendaItems(agendaScreenState.selectedDate)
            }

            AgendaScreen(
                agendaScreenState = agendaScreenState,
                agendaScreenEvent = { agendaScreenEvent ->
                    agendaViewModel.onAgendaScreenEvent(agendaScreenEvent)
                },
            onSelectedAgendaItem = {
                /* TODO The item in the dropdown menu should be an enum or a sealed class that will determine which item was clicked
                *   i.e navHostController.navigate(Screen.TaskScreen.route) */
                navHostController.navigate(Screen.EventScreen.route)
            },
            onSelectedEditAgendaItemClicked = { eventId, agendaType, agendaMenuActionType ->
                when(agendaType) {
                    AgendaType.EVENT -> {
                        when(agendaMenuActionType) {
                            AgendaMenuActionType.OPEN -> {
                                val open = AgendaMenuActionType.OPEN.name
                                navHostController.navigate(route = "${Screen.EventScreen.EVENT_SCREEN}/$eventId/${AgendaMenuActionType.OPEN}")
                            }
                            AgendaMenuActionType.EDIT -> {
                                navHostController.navigate(Screen.EventScreen.route)
                            }
                            AgendaMenuActionType.DELETE -> {
                                agendaViewModel.deleteEventById(eventId)
                            }
                        }
                    }
                    AgendaType.TASK -> TODO()
                    AgendaType.REMINDER -> TODO()
                }
            },
                onLogout = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.LoginScreen.route)
                })
        }

        /* Event Detail Screen */
        composable(
            route = Screen.EventScreen.route,
            arguments = listOf(navArgument(EVENT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }, navArgument(MENU_ACTION_TYPE) {
                type = NavType.StringType
                defaultValue = AgendaMenuActionType.OPEN.name
                }),
            deepLinks = listOf(navDeepLink {
                this.uriPattern = AgendaDeepLinks.EVENT_DEEPLINK
            })
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

            /** Once the insertion has completed the state will change to true then
             *  we will know that the insertion has completed and we can popbackstack
             *  to go back to the agenda screen */
            LaunchedEffect(key1 = eventScreenState.isSaved) {
                if(eventScreenState.isSaved) {
                    navHostController.popBackStack()
                }
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
                },
                onCloseClicked = {
                    navHostController.popBackStack()
                },
            )
        }
        
        /* Edit Screen */
        composable(
            route = Screen.EditScreen.route,
            arguments = listOf(navArgument(CONTENT) {
                type = NavType.StringType
            }, navArgument(CONTENT_TYPE) {
                type = NavType.StringType
            })
        ) {
            val editScreenViewModel: EditScreenViewModel = hiltViewModel()
            val editScreenState by editScreenViewModel.editScreenState.collectAsStateWithLifecycle()
            val content = it.arguments?.getString(CONTENT) ?: ""

            /** Get the contentType that has been edited which could be either the Title or the Description
             *  If null then return the default title instead */
            val contentType = it.arguments?.getString(CONTENT_TYPE)?.let { contentType ->
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