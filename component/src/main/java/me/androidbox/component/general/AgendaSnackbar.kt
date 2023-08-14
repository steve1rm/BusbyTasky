package me.androidbox.component.general

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.LightGreen

@Composable
fun AgendaSnackbar(
    snackbarHostState: SnackbarHostState,
    onAction: () -> Unit = {},
    onDismiss: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier) { data ->

        Snackbar(
            modifier = Modifier
                .padding(12.dp),
            action = {
                TextButton(
                    onClick = {
                        onAction()
                    }
                ) {
                    Text(
                        text = data.visuals.actionLabel ?: "",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LightGreen) }
            },
            dismissAction = {
                onDismiss()
            }
        ) {
            Text(
                text = data.visuals.message,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaSnackbar() {
    BusbyTaskyTheme {
        AgendaSnackbar(
            snackbarHostState = SnackbarHostState(),
            onAction = {},
            onDismiss = {}
        )
    }
}