package me.androidbox.presentation.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.model.Reminder
import me.androidbox.domain.agenda.model.Task
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.domain.constant.AgendaDeepLinks
import me.androidbox.domain.toInitials
import me.androidbox.presentation.agenda.constant.AgendaMenuActionType
import me.androidbox.presentation.agenda.screen.AgendaScreen
import me.androidbox.presentation.agenda.viewmodel.AgendaViewModel
import me.androidbox.presentation.edit.screen.ContentType
import me.androidbox.presentation.edit.screen.EditScreen
import me.androidbox.presentation.edit.screen.EditScreenEvent
import me.androidbox.presentation.edit.viewmodel.EditScreenViewModel
import me.androidbox.presentation.event.screen.EventScreen
import me.androidbox.presentation.event.screen.EventScreenEvent
import me.androidbox.presentation.event.viewmodel.EventViewModel
import me.androidbox.presentation.navigation.Screen.Companion.ID
import me.androidbox.presentation.navigation.Screen.Companion.MENU_ACTION_TYPE
import me.androidbox.presentation.navigation.Screen.EditScreen.CONTENT
import me.androidbox.presentation.navigation.Screen.EditScreen.CONTENT_TYPE
import me.androidbox.presentation.navigation.Screen.PhotoScreen.PHOTO_IMAGE_PATH
import me.androidbox.presentation.navigation.Screen.TaskDetailScreen.TASK_DETAIL_SCREEN
import me.androidbox.presentation.photo.screen.PhotoScreen
import me.androidbox.presentation.photo.viewmodel.PhotoScreenViewModel
import me.androidbox.presentation.task.screen.TaskDetailScreen
import me.androidbox.presentation.task.screen.TaskDetailScreenEvent
import me.androidbox.presentation.task.viewmodel.TaskDetailViewModel

fun NavGraphBuilder.agendaNavigationGraph(navHostController: NavHostController) {
    navigation(
        startDestination = Screen.AgendaScreen.route,
        route = Screen.Agenda.route
    ) {
        composable(
            route = Screen.AgendaScreen.route
        ) {
            val agendaViewModel: AgendaViewModel = hiltViewModel()
            val agendaScreenState by agendaViewModel.agendaScreenState.collectAsStateWithLifecycle()

            /** FIXME There is an issue as insertion, deletion should automatically trigger collectLatest flow */
            LaunchedEffect(key1 = agendaScreenState.selectedDay) {
                agendaViewModel.fetchAgendaItems(agendaScreenState.selectedDay)
            }

            AgendaScreen(
                agendaScreenState = agendaScreenState,
                agendaScreenEvent = { agendaScreenEvent ->
                    agendaViewModel.onAgendaScreenEvent(agendaScreenEvent)
                },
                onSelectedAgendaItem = { agendaItem ->
                    when (agendaItem) {
                        AgendaType.EVENT.ordinal -> {
                            navHostController.navigate(Screen.EventScreen.route + "?=menuActionType=${AgendaMenuActionType.OPEN.name}")
                        }

                        AgendaType.TASK.ordinal -> {
                            navHostController.navigate(Screen.TaskDetailScreen.route)
                        }

                        AgendaType.REMINDER.ordinal -> {
                            TODO("Not Implemented yet")
                        }
                    }
                },
                onSelectedEditAgendaItemClicked = { agendaItem, agendaMenuActionType ->
                    val routeDestination = when (agendaItem) {
                        is Event -> {
                            Screen.EventScreen.EVENT_DETAIL_SCREEN
                        }

                        is Task -> {
                            TASK_DETAIL_SCREEN
                        }

                        is Reminder -> TODO("Not Implemented yet")
                    }

                    if (agendaMenuActionType == AgendaMenuActionType.DELETE) {
                        when (agendaItem) {
                            is Event -> {
                                agendaViewModel.deleteEventById(eventId = agendaItem.id)
                            }

                            is Task -> {
                                agendaViewModel.deleteTaskById(taskId = agendaItem.id)
                            }

                            is Reminder -> {
                                // Not implemented yet agendaViewModel.deleteReminderById(taskId = agendaItem.id)
                            }
                        }
                    } else {
                        navHostController.navigate(route = "${routeDestination}?id=${agendaItem.id}&menuActionType=${agendaMenuActionType.name}")
                    }
                },
                onLogout = {
                    navHostController.popBackStack()
                    navHostController.navigate(Screen.LoginScreen.route)
                })
        }

        /* Event Detail Screen */
        composable(
            route = "event_screen?id={id}&menuActionType={menuActionType}",
            arguments = listOf(navArgument(ID) {
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
            val description = it.savedStateHandle.get<String>(ContentType.DESCRIPTION.name)
                ?: "New Description"
            val selectedPhoto = it.savedStateHandle.get<String>(PHOTO_IMAGE_PATH)

            LaunchedEffect(key1 = title, key2 = description) {
                eventViewModel.onEventScreenEvent(
                    EventScreenEvent.OnSaveTitleOrDescription(
                        title,
                        description
                    )
                )
            }

            LaunchedEffect(key1 = selectedPhoto) {
                if (selectedPhoto != null) {
                    eventViewModel.onEventScreenEvent(
                        EventScreenEvent.OnPhotoDeletion(
                            photo = selectedPhoto
                        )
                    )
                }
            }

            /** Once the insertion has completed the state will change to true then
             *  we will know that the insertion has completed and we can popbackstack
             *  to go back to the agenda screen */
            LaunchedEffect(key1 = eventScreenState.isSaved) {
                if (eventScreenState.isSaved) {
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
                onPhotoClicked = { photoImage ->
                    val encodedImagePath = Uri.encode(photoImage)
                    navHostController.navigate(route = "${Screen.PhotoScreen.PHOTO_SCREEN}/${encodedImagePath}")
                },
                toInitials = { fullName ->
                    fullName.toInitials()
                }
            )
        }

        /* Task Detail Screen */
        composable(
            route = Screen.TaskDetailScreen.route,
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }, navArgument(MENU_ACTION_TYPE) {
                    type = NavType.StringType
                    defaultValue = AgendaMenuActionType.OPEN.name
                }),
            deepLinks = listOf(navDeepLink {
                uriPattern = AgendaDeepLinks.TASK_DEEPLINK
            })
        ) {
            val taskDetailViewModel: TaskDetailViewModel = hiltViewModel()
            val taskDetailScreenState by taskDetailViewModel.taskDetailScreenState.collectAsStateWithLifecycle()
            val title = it.savedStateHandle.get<String>(ContentType.TITLE.name) ?: "New Task"
            val description =
                it.savedStateHandle.get<String>(ContentType.DESCRIPTION.name) ?: "Description"

            LaunchedEffect(key1 = title, key2 = description) {
                taskDetailViewModel.onTaskDetailScreenEvent(
                    TaskDetailScreenEvent.OnSaveTitleOrDescription(
                        title = title,
                        description = description
                    )
                )
            }

            /** Once the insertion has completed the state will change to true then
             *  we will know that the insertion has completed and we can popbackstack
             *  to go back to the agenda screen */
            LaunchedEffect(key1 = taskDetailScreenState.isSaved) {
                if (taskDetailScreenState.isSaved) {
                    navHostController.popBackStack()
                }
            }

            TaskDetailScreen(
                taskDetailScreenState = taskDetailScreenState,
                taskDetailScreenEvent = { taskDetailScreenEvent ->
                    taskDetailViewModel.onTaskDetailScreenEvent(taskDetailScreenEvent)
                },
                onEditTitleClicked = { title ->
                    navHostController.navigate(route = "${Screen.EditScreen.EDIT_SCREEN}/$title/${ContentType.TITLE}")
                },
                onEditDescriptionClicked = { description ->
                    navHostController.navigate(route = "${Screen.EditScreen.EDIT_SCREEN}/$description/${ContentType.DESCRIPTION}")
                },
                onCloseClicked = {
                    navHostController.popBackStack()
                },
                modifier = Modifier.fillMaxSize()
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
                when (contentType) {
                    ContentType.TITLE.name -> ContentType.TITLE
                    ContentType.DESCRIPTION.name -> ContentType.DESCRIPTION
                    else -> {
                        ContentType.TITLE
                    }
                }
            } ?: ContentType.TITLE

            LaunchedEffect(key1 = content) {
                editScreenViewModel.onEditScreenEvent(
                    editScreenEvent = EditScreenEvent.OnContentChanged(content)
                )
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
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        contentType.name,
                        content
                    )
                    navHostController.popBackStack()
                }
            )
        }

        /** Photo Screen */
        composable(
            route = Screen.PhotoScreen.route,
            arguments = listOf(navArgument(PHOTO_IMAGE_PATH) {
                this.type = NavType.StringType
                this.defaultValue = null
                this.nullable = true
            })
        ) {
            val photoScreenViewModel: PhotoScreenViewModel = hiltViewModel()
            val photoScreenState by photoScreenViewModel.photoScreenState.collectAsStateWithLifecycle()

            PhotoScreen(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                photoScreenState = photoScreenState,
                photoScreenEvent = { photoScreenEvent ->
                    photoScreenViewModel.onPhotoScreenEvent(photoScreenEvent)
                },
                onCloseClicked = {
                    navHostController.previousBackStackEntry?.savedStateHandle?.clearSavedStateProvider(
                        PHOTO_IMAGE_PATH
                    )
                    navHostController.popBackStack()
                },
                onSelectedPhotoForDeletion = { selectedPhoto ->
                    navHostController.previousBackStackEntry?.savedStateHandle?.set(
                        PHOTO_IMAGE_PATH,
                        selectedPhoto
                    )
                    navHostController.popBackStack()
                }
            )
        }
    }
}