package me.androidbox.component.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.TaskButton
import me.androidbox.component.general.UserInputTextField
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor

@Composable
fun AddVisitorDialogContent(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChanged: (email: String) -> Unit,
    onDialogClose: () -> Unit,
    onTaskButtonClicked: () -> Unit) {

    Column(modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    onDialogClose()
                }) {
                Icon(painter = painterResource(id = R.drawable.close), contentDescription = "close button")
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = stringResource(R.string.add_visitor),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.agendaBodyTextColor)

        Spacer(modifier = Modifier.height(30.dp))

        UserInputTextField(
            isInputValid = true,
            inputValue = email,
            placeholderText = stringResource(id = R.string.email_address),
            onInputChange = { newEmail ->
                onEmailChanged(newEmail)
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        TaskButton(
            buttonText = stringResource(R.string.add),
            onButtonClick = {
                onTaskButtonClicked()
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorNotification() {
    BusbyTaskyTheme {
        AddVisitorDialogContent(
        modifier = Modifier,
        email = "steve@gmail.com",
        onEmailChanged = {},
        onDialogClose = {},
        onTaskButtonClicked = {}
        )
    }
}