package me.androidbox.component.agenda

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.TaskButton
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor

@Composable
fun VisitorFilter(
    modifier: Modifier = Modifier,
    onAllClicked: () -> Unit,
    onGoingClicked: () -> Unit,
    onNotGoingClicked: () -> Unit) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text =" Visitors",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.agendaBodyTextColor)

        Spacer(modifier = Modifier.height(36.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
            TaskButton(
                modifier = Modifier.height(36.dp).width(120.dp),
                textSize = 14.sp,
                buttonText = stringResource(R.string.all), onButtonClick = {
                    onAllClicked()
                })

            TaskButton(
                modifier = Modifier.height(36.dp).width(120.dp),
                textSize = 14.sp,
                buttonText = stringResource(R.string.going), onButtonClick = {
                    onGoingClicked()
                })

            TaskButton(
                modifier = Modifier.height(36.dp).width(120.dp),
                textSize = 14.sp,
                buttonText = stringResource(R.string.not_going), onButtonClick = {
                    onNotGoingClicked()
                })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorFilter() {
    BusbyTaskyTheme {
        VisitorFilter(
            onAllClicked = {},
            onGoingClicked = {},
            onNotGoingClicked = {}
        )
    }
}