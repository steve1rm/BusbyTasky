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
import me.androidbox.component.general.UserInputEntry
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor

@Composable
fun AddVisitorNotification(modifier: Modifier = Modifier) {

    var emailAddress by remember {
        mutableStateOf("")
    }

    Column(modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    /** TODO Close the notification */
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

        UserInputEntry(
            isInputValid = true,
            inputValue = emailAddress,
            placeholderText = stringResource(id = R.string.email_address),
            onInputChange = { newInput ->
                emailAddress = newInput
                /** TODO Check is a correct email address */
            }
        )

        Spacer(modifier = Modifier.height(30.dp))

        TaskButton(
            buttonText = stringResource(R.string.add),
            onButtonClick = { /*TODO*/ }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddVisitorNotification() {
    BusbyTaskyTheme {
        AddVisitorNotification()
    }
}