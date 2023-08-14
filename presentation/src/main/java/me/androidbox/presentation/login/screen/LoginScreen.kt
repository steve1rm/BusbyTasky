package me.androidbox.presentation.login.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.androidbox.component.R
import me.androidbox.component.general.AgendaSnackbar
import me.androidbox.component.general.PasswordTextField
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputTextField
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundInputEntry
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.AuthenticatedUser

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenEvent: (AuthenticationScreenEvent) -> Unit,
    onLoginSuccess: (login: AuthenticatedUser) -> Unit,
    onSignUpClicked: () -> Unit,
    authenticationUserState: AuthenticationScreenState<AuthenticatedUser>,
    validateCredentialsState: AuthenticationScreenState<AuthenticatedUser>
) {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val coroutineScope = rememberCoroutineScope()
    var rememberSnackBarJob: Job? by remember {
        mutableStateOf(null)
    }

    LaunchedEffect(key1 = authenticationUserState.responseState) {
        when (val status = authenticationUserState.responseState) {
            is ResponseState.Loading -> {
                loginScreenEvent(AuthenticationScreenEvent.OnIsLoading(isLoading = true))
            }
            is ResponseState.Success -> {
                loginScreenEvent(AuthenticationScreenEvent.OnIsLoading(isLoading = false))
                onLoginSuccess(status.data)
            }
            is ResponseState.Failure -> {
                loginScreenEvent(AuthenticationScreenEvent.OnIsLoading(isLoading = false))
                rememberSnackBarJob?.cancel()
                rememberSnackBarJob = coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = status.error ?: "Unknown Error please try again",
                        duration = SnackbarDuration.Long
                    )
                }
            }
            else -> Unit
        }
    }

    Scaffold(modifier = modifier
        .fillMaxSize()
        .background(Color.Black),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(it)
            ) {
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
                            inputValue = validateCredentialsState.email,
                            isInputValid = validateCredentialsState.isValidEmail,
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
                            passwordValue = validateCredentialsState.password,
                            placeholderText = stringResource(R.string.password),
                            onPasswordChange = { newPassword ->
                                loginScreenEvent(AuthenticationScreenEvent.OnPasswordChanged(newPassword))
                            },
                            onPasswordVisibilityClicked = {
                                loginScreenEvent(AuthenticationScreenEvent.OnPasswordVisibilityChanged)
                            },
                            isPasswordVisible = authenticationUserState.isPasswordVisible
                        )

                        Spacer(modifier = Modifier.height(26.dp))

                        TaskButton(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            buttonText = stringResource(R.string.login).uppercase(),
                            onButtonClick = {
                                loginScreenEvent(AuthenticationScreenEvent.OnAuthenticationUser)
                            },
                            isLoading = authenticationUserState.isLoading
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                    ) {
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
        },
        snackbarHost = {
            AgendaSnackbar(
                snackbarHostState = snackbarHostState)
        })
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
        LoginScreen(
            onSignUpClicked = {},
            onLoginSuccess = {},
            loginScreenEvent = {},
            authenticationUserState = AuthenticationScreenState(),
            validateCredentialsState = AuthenticationScreenState()
        )
    }
}
