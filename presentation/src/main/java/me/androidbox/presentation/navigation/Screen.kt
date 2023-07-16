package me.androidbox.presentation.navigation

sealed class Screen(val route: String) {

    companion object {
        const val ID = "id"
        const val MENU_ACTION_TYPE = "menuActionType"
    }

    object LoginScreen : Screen(route = "login_screen")

    object RegisterScreen : Screen(route = "register_screen")

    object AgendaScreen : Screen(route = "agenda_screen")

    object EditScreen : Screen(route = "edit_screen/{content}/{contentType}") {
        const val EDIT_SCREEN = "edit_screen"
        const val CONTENT = "content"
        const val CONTENT_TYPE = "contentType"
    }

    object EventScreen : Screen(route = "event_screen?id={id}&menuActionType={menuActionType}") {
        const val EVENT_DETAIL_SCREEN = "event_screen"
    }

    object TaskDetailScreen : Screen(route = "task_detail_screen/{id}/{menuActionType}") {
        const val TASK_DETAIL_SCREEN = "task_detail_screen"
    }

    object PhotoScreen : Screen(route = "photo_screen/{imagePath}") {
        const val PHOTO_SCREEN = "photo_screen"
        const val PHOTO_IMAGE_PATH = "imagePath"
    }

    /** Nested Navigation routes */
    object Authentication : Screen(route = "authentication")
    object Agenda : Screen(route = "agenda")
}
