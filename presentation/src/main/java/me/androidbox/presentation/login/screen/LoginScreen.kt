package me.androidbox.presentation.login.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import me.androidbox.component.R
import me.androidbox.component.general.PasswordEntry
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputEntry
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundInputEntry
import me.androidbox.presentation.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Black)) {

        Spacer(modifier = modifier.height(48.dp))

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Welcome Back!", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)

        Spacer(modifier = modifier.height(42.dp))

        Box {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = modifier.height(50.dp))
                UserInputEntry(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.backgroundInputEntry
                        ),
                    inputValue = loginViewModel.username,
                    isInputValid = false,
                    placeholderText = stringResource(R.string.name),
                    onInputChange = { newUsername ->
                        loginViewModel.onUsernameChanged(newUsername)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                PasswordEntry(
                    passwordValue = loginViewModel.password,
                    placeholderText = stringResource(R.string.password),
                    onPasswordChange = { newPassword ->
                        loginViewModel.onPasswordChanged(newPassword)
                    },
                    onPasswordVisibilityClicked = {
                        loginViewModel.onPasswordVisibilityChanged()
                    },
                    isPasswordVisible = loginViewModel.isPasswordVisible
                )

                Spacer(modifier = Modifier.height(26.dp))

                TaskButton(
                    modifier = Modifier.height(50.dp).fillMaxWidth(),
                    buttonText = stringResource(R.string.login).uppercase(),
                    onButtonClick = {
                        /** TODO send to room database username and password check if correct/incorrect */
                        Log.d("LOGIN", "username [${loginViewModel.username}] [${loginViewModel.password}]")
                    }
                )
            }
            
            Column(modifier = modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                // TODO Use annotated string
                ClickableText(
                    style = TextStyle(textAlign = TextAlign.Center),
                    modifier = modifier.fillMaxWidth(),
                  //  text = "Don't have a an account? SIGN UP".uppercase(), color = Color.Black,
                    text = buildLoginAnnotatedString(),
                    onClick = { position ->
                        Log.d("LOGIN", "$position")
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

private fun buildLoginAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            SpanStyle(color = Color.Black)
        ) {
            append("Don't have a an account?")
        }
        
        append(" ")

        withStyle(
            SpanStyle(color = Color.Blue)
        ) {
            append("sign up")
        }
    }.toUpperCase()
}

@Composable
@Preview(showBackground = true)
fun PreviewLoginScreen() {
    BusbyTaskyTheme {
       LoginScreen()
    }
}
