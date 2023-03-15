package me.androidbox.presentation.login.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import me.androidbox.component.R
import me.androidbox.component.general.PasswordTextField
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputTextField
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundInputEntry
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Login

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenEvent: (AuthenticationScreenEvent) -> Unit,
    onLoginSuccess: (login: Login) -> Unit,
    onSignUpClicked: () -> Unit,
    authenticationScreenState: State<AuthenticationScreenState>
) {

    LaunchedEffect(key1 = authenticationScreenState.value.loginResponseState) {
        when(val status = authenticationScreenState.value.loginResponseState) {
            is ResponseState.Loading -> {
                /* TODO Showing loading spinner */
            }
            is ResponseState.Success -> {
                onLoginSuccess(status.data)
            }
            is ResponseState.Failure -> {
                /* Failure */
                Log.d("LOGIN","${status.error}")
            }
            else -> Unit
        }
    }

    Column(modifier = modifier
        .fillMaxSize()
        .background(Color.Black)) {

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(me.androidbox.presentation.R.string.welcome_back),
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(42.dp))

        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                UserInputTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.backgroundInputEntry
                        ),
                    inputValue = authenticationScreenState.value.email,
                    isInputValid = false,
                    placeholderText = stringResource(R.string.email_address),
                    onInputChange = { newEmail ->
                        loginScreenEvent(AuthenticationScreenEvent.OnEmailChanged(newEmail))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.backgroundInputEntry
                        ),
                    passwordValue = authenticationScreenState.value.password,
                    placeholderText = stringResource(R.string.password),
                    onPasswordChange = { newPassword ->
                        loginScreenEvent(AuthenticationScreenEvent.OnPasswordChanged(newPassword))
                    },
                    onPasswordVisibilityClicked = {
                        loginScreenEvent(AuthenticationScreenEvent.OnPasswordVisibilityChanged)
                    },
                    isPasswordVisible = authenticationScreenState.value.isPasswordVisible
                )

                Spacer(modifier = Modifier.height(26.dp))

                TaskButton(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    buttonText = stringResource(R.string.login).uppercase(),
                    onButtonClick = {
                        loginScreenEvent(AuthenticationScreenEvent.OnAuthenticationUser)
                    }
                )
            }
            
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)) {
                ClickableText(
                    style = TextStyle(textAlign = TextAlign.Center),
                    modifier = modifier.fillMaxWidth(),
                    text = buildLoginAnnotatedString(),
                    onClick = { _ ->
                        onSignUpClicked()
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
        /* TODO Fix these */
   /*    LoginScreen(
           onSignUpClicked = {},
           onLoginSuccess = {}
       )*/
    }
}
