package me.androidbox.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val isPasswordVisible = remember {
        mutableStateOf(false)
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Black)) {

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Welcome Back!", color = Color.White)

        Spacer(modifier = modifier.height(62.dp))
        Box {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
            ) {

                Spacer(modifier = modifier.height(32.dp))
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

            Text(
                modifier = modifier.align(Alignment.BottomCenter),
                text = "Don't have a an account? SIGN UP", color = Color.Black
            ) // TODO Use annotated string
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginScreen() {
    BusbyTaskyTheme {
       LoginScreen()
    }
}
