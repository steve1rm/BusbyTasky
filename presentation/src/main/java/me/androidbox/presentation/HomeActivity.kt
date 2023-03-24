package me.androidbox.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.presentation.login.viewmodel.HomeViewModel
import me.androidbox.presentation.navigation.NavigationGraph
import me.androidbox.presentation.navigation.Screen
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme
import me.androidbox.presentation.worker.UploadEventWorker
import java.util.*

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeViewModel by viewModels<HomeViewModel>()

        val uploadEventWorkerRequest = OneTimeWorkRequestBuilder<UploadEventWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED).build()
            ).build()

        val workManager = WorkManager.getInstance(applicationContext)

        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                homeViewModel.authenticationState.value == null
            }
        }

        setContent {
            val workInfos by workManager.getWorkInfosForUniqueWorkLiveData("upload_event").observeAsState()
            
            val downloadInfo = remember(key1 = workInfos) {
                workInfos?.find { workInfo ->
                    workInfo.id == uploadEventWorkerRequest.id
                }
            }

            /** TODO Just testing using work manager to send a event request */
            LaunchedEffect(key1 = true, block = {
                workManager.beginUniqueWork("upload_event", ExistingWorkPolicy.KEEP, uploadEventWorkerRequest).enqueue()
            })

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
