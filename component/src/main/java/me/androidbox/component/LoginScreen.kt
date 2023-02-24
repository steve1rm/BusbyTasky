package me.androidbox.component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.androidbox.component.general.PasswordEntry
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputEntry
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier
) {

    var username by remember {
        mutableStateOf("")
    }

    var isPasswordVisible = remember {
        mutableStateOf(false)
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()) {
        UserInputEntry(
            inputValue = username,
            isInputValid = false,
            placeholderText = "name",
            onInputChange = { newText ->
                username = newText
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        PasswordEntry(
            passwordValue = password,
            placeholderText = "Password", // FIXME stringResource(R.string.password),
            isPasswordVisible = isPasswordVisible,
            onPasswordChange = { newText ->
                password = newText
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TaskButton(
            buttonText = "Login", // FIXME stringResource(R.string.login),
            onButtonClick = {
                /* TODO send to room database username and password check if correct/incorrect */
                Log.d("LOGIN", "username [$username] [$password]")
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginScreen() {
    BusbyTaskyTheme {
       LoginScreen()
    }
}
