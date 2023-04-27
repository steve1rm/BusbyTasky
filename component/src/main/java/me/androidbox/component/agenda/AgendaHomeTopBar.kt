package me.androidbox.component.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.topbarButtonBackgroundColor
import me.androidbox.component.ui.theme.topbarFontColor

@Composable
fun AgendaTopBar(
    initials: String,
    displayMonth: String,
    onProfileButtonClicked: () -> Unit,
    onDateClicked: () -> Unit,
    modifier: Modifier = Modifier,
    dropDropMenuScope: @Composable () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {

        Text(
            modifier = Modifier.clickable {
                onDateClicked()
            },
            text = displayMonth,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.topbarFontColor
        )

        Box {
            Button(
                modifier = Modifier.size(34.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.topbarButtonBackgroundColor,
                    contentColor = MaterialTheme.colorScheme.topbarFontColor),
                onClick = {
                    onProfileButtonClicked()
                }) {
                Text(
                    maxLines = 1,
                    text = initials,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            dropDropMenuScope()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaTopBar() {
    BusbyTaskyTheme {
        AgendaTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                .padding(horizontal = 16.dp),
            displayMonth = "August",
            initials = "SM",
            onDateClicked = {},
            onProfileButtonClicked = {},
            dropDropMenuScope = {}
        )
    }
}