package me.androidbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        /**
         *  Authentication Graph */
        authenticationGraph(navHostController)

        /**
         *  Agenda Graph */
        agendaNavigationGraph(navHostController)
    }
}