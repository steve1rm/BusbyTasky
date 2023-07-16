package me.androidbox.component.general

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import me.androidbox.component.R
import me.androidbox.component.ui.theme.Black
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.White
import me.androidbox.component.ui.theme.agendaBodyTextColor

@Composable
fun AgendaBottomSheetToolbar(
    @StringRes title: Int,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(id = title),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.agendaBodyTextColor
        )

        IconButton(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd),
            onClick = {
                onCloseClicked()
            }
        ) {
            Icon(
                painterResource(id = R.drawable.close),
                contentDescription = "Close the bottom sheet",
                tint = Black
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewToolbar() {
    BusbyTaskyTheme {
        AgendaBottomSheetToolbar(
            title = R.string.alarm_reminder,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White)
                .padding(vertical = 16.dp, horizontal = 16.dp),
            onCloseClicked = {})
    }
}
