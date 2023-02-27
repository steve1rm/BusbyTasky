package me.androidbox.component.event

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.agenda.*
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@Composable
fun EventScreen(
    modifier: Modifier = Modifier) {

    Column(modifier = modifier
        .fillMaxWidth()
        .background(Color.Black)) {

        Spacer(modifier = modifier.height(48.dp))

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "Welcome Back!",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = modifier.height(20.dp))

        Column (modifier = modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
            .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = modifier.height(30.dp))
            AgendaHeader(agendaHeaderItem = AgendaHeaderItem.EVENT, subTitle = "Meeting")

            Spacer(modifier = modifier.height(26.dp))
            AgendaDuration()

            Spacer(modifier = modifier.height(26.dp))
            AlarmReminder()

            Spacer(modifier = modifier.height(26.dp))
            VisitorFilter(onAllClicked = { /*TODO*/ }, onGoingClicked = { /*TODO*/ }) {

            }

            Visitor(visitorInitials = "SM", visitorName = "Steve Mason") {
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewEventScreen() {
    BusbyTaskyTheme {
        EventScreen(Modifier)
    }
}