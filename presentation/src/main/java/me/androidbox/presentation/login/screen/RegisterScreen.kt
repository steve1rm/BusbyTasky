package me.androidbox.presentation.login.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import me.androidbox.domain.authentication.model.AuthenticatedUser

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    registerScreenState: AuthenticationScreenState<Unit>,
    validateCredentialsState: AuthenticationScreenState<AuthenticatedUser>,
    loginScreenEvent: (AuthenticationScreenEvent) -> Unit,
    onBackArrowClicked: () -> Unit,
    onRegistrationSuccess: () -> Unit)
{

    LaunchedEffect(key1 = registerScreenState) {
        when (val status = registerScreenState.responseState) {
            ResponseState.Loading -> {
                /* TODO Show a loading progress spinner */
            }

            is ResponseState.Success -> {
                /* Navigate back to login screen */
                onRegistrationSuccess()
            }

            is ResponseState.Failure -> {
                Log.d("REGISTRATION", "Failed to register ${status.error}")
            }
            else -> Unit
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Create your account",
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

                /* Name */
                UserInputTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.backgroundInputEntry
                        ),
                    inputValue = registerScreenState.username,
                    isInputValid = false,
                    placeholderText = stringResource(R.string.name),
                    onInputChange = { newUsername ->
                        loginScreenEvent(AuthenticationScreenEvent.OnUsernameChanged(newUsername))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                /* Email */
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

                /* Password */
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.backgroundInputEntry
                        ),
                    passwordValue = validateCredentialsState.password,
                    placeholderText = stringResource(R.string.password),
                    isPasswordVisible = registerScreenState.isPasswordVisible,
                    onPasswordChange = { newPassword ->
                        loginScreenEvent(AuthenticationScreenEvent.OnPasswordChanged(newPassword))
                    },
                    onPasswordVisibilityClicked = {
                        loginScreenEvent(AuthenticationScreenEvent.OnPasswordVisibilityChanged)
                    }
                )

                Spacer(modifier = Modifier.height(26.dp))

                TaskButton(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    buttonText = stringResource(R.string.get_started).uppercase(),
                    onButtonClick = {
                        loginScreenEvent(AuthenticationScreenEvent.OnRegisterUser)
                    }
                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)) {

                IconButton(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .size(width = 56.dp, height = 56.dp)
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(size = 16.dp)
                        ),
                    onClick = {
                        onBackArrowClicked()
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow),
                        contentDescription = "Arrow back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewRegisterScreen() {
    BusbyTaskyTheme {
        RegisterScreen(
            onBackArrowClicked = {},
            onRegistrationSuccess = {},
            loginScreenEvent = {},
            registerScreenState = AuthenticationScreenState(),
            validateCredentialsState = AuthenticationScreenState()
        )
    }
}
