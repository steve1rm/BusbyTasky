package me.androidbox.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import me.androidbox.presentation.login.screen.LoginScreen
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusbyTaskyTheme {
                LoginScreen {

                }
            }
        }
    }
}
