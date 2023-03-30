package me.androidbox.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.presentation.event.viewmodel.EventViewModel
import me.androidbox.presentation.login.viewmodel.HomeViewModel
import me.androidbox.presentation.navigation.NavigationGraph
import me.androidbox.presentation.navigation.Screen
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel by viewModels<HomeViewModel>()

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                homeViewModel.authenticationState.value == null
            }
        }

        /* TODO Just for testing inserting and fetching */
        val eventViewModel by viewModels<EventViewModel>()
        eventViewModel.insertEvent()

        setContent {
            val authenticatedState = homeViewModel.authenticationState.collectAsState()
            val destination = when(authenticatedState.value) {
                is ResponseState.Success -> {
                    /* TODO Go to the Agenda Screen when implemented */
                    Screen.AgendaScreen.route
                }
                else -> {
                    Screen.AgendaScreen.route
                    // TODO for testing purposes that skip to the agendaScreen. // Screen.LoginScreen.route
                }
            }

            BusbyTaskyTheme {
                val navHostController = rememberNavController()
                NavigationGraph(navHostController = navHostController, startDestination = destination)
            }
        }
    }
}
