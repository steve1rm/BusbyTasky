package me.androidbox.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.presentation.login.viewmodel.SplashScreenViewModel
import me.androidbox.presentation.navigation.LoginScreen
import me.androidbox.presentation.navigation.NavigationGraph
import me.androidbox.presentation.navigation.RegisterScreen
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreenViewModel by viewModels<SplashScreenViewModel>()

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                splashScreenViewModel.isCompletedState.value
            }
        }

        splashScreenViewModel.authenticateUser()

        setContent {
            val authenticatedState = splashScreenViewModel.authenticationState.collectAsState()
            val destination = when(authenticatedState.value) {
                is ResponseState.Success -> {
                    /* TODO Go to the Agenda Screen when implemented */
                    RegisterScreen.route
                }
                else -> {
                    LoginScreen.route
                }
            }

            BusbyTaskyTheme {
                val navHostController = rememberNavController()
                NavigationGraph(navHostController = navHostController, startDestination = destination)
            }
        }
    }
}
