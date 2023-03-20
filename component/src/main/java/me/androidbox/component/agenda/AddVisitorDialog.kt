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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import me.androidbox.component.R
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputTextField
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.backgroundColor

@Composable
fun AddVisitorDialog(
    email: String,
    onEmailChanged: (email: String) -> Unit,
    onDialogClose: () -> Unit,
    isValidInput: Boolean,
    onAddButtonClicked: (email: String) -> Unit,
    modifier: Modifier = Modifier) {

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
                text = stringResource(R.string.add_visitor),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor
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

            Spacer(modifier = Modifier.height(30.dp))

            TaskButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(R.string.add),
                onButtonClick = {
                    onAddButtonClicked(email)
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorDialog() {
    BusbyTaskyTheme {
        AddVisitorDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.backgroundColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(20.dp),
            email = "joeblogs@gmail.com",
            isValidInput = true,
            onEmailChanged = {},
            onDialogClose = {},
            onAddButtonClicked = {}
        )
    }
}