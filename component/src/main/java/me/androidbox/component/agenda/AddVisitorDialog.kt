package me.androidbox.component.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import me.androidbox.component.R
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputTextField
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaSubTitleHeaderColor
import me.androidbox.component.ui.theme.backgroundWhiteColor

@Composable
fun AddVisitorDialog(
    email: String,
    isEmailVerified: Boolean,
    onEmailChanged: (email: String) -> Unit,
    onDialogClose: () -> Unit,
    isValidInput: Boolean,
    isAlreadyAdded: Boolean,
    onAddButtonClicked: (email: String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false) {

    Dialog(onDismissRequest = { onDialogClose() }) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onDialogClose()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "close button"
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.add_visitor),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.agendaSubTitleHeaderColor
            )

            Spacer(modifier = Modifier.height(30.dp))

            UserInputTextField(
                modifier = Modifier.fillMaxWidth(),
                isInputValid = isValidInput,
                inputValue = email,
                placeholderText = stringResource(id = R.string.email_address),
                onInputChange = { newEmail ->
                    onEmailChanged(newEmail)
                }
            )

            /** TODO https://github.com/steve1rm/BusbyTasky/pull/52#discussion_r1190085164 */
            val errorMessage = when {
                isAlreadyAdded -> {
                    stringResource(R.string.visitor_already_added)
                }
                !isEmailVerified -> {
                    stringResource(id = R.string.email_verify_failed)
                }
                else -> {
                    null
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = errorMessage ?: "",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(30.dp))

            TaskButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(R.string.add),
                isLoading = isLoading,
                onButtonClick = {
                    onAddButtonClicked(email)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorDialogEmailNotExist() {
    BusbyTaskyTheme {
        AddVisitorDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.backgroundWhiteColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            email = "joeblogs@gmail.com",
            isEmailVerified = false,
            isValidInput = true,
            isAlreadyAdded = false,
            onEmailChanged = {},
            onDialogClose = {},
            onAddButtonClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorDialogEmailExists() {
    BusbyTaskyTheme {
        AddVisitorDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.backgroundWhiteColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            email = "joeblogs@gmail.com",
            isEmailVerified = true,
            isValidInput = true,
            isAlreadyAdded = false,
            onEmailChanged = {},
            onDialogClose = {},
            onAddButtonClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorDialogDuplicateVisitor() {
    BusbyTaskyTheme {
        AddVisitorDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.backgroundWhiteColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            email = "joeblogs@gmail.com",
            isEmailVerified = true,
            isValidInput = true,
            isAlreadyAdded = true,
            onEmailChanged = {},
            onDialogClose = {},
            onAddButtonClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorDialogInvalidEmail() {
    BusbyTaskyTheme {
        AddVisitorDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.backgroundWhiteColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            email = "joeblogs@mail.c",
            isEmailVerified = false,
            isAlreadyAdded = false,
            isValidInput = false,
            onEmailChanged = {},
            onDialogClose = {},
            onAddButtonClicked = {},
            isLoading = false
        )
    }
}