package me.androidbox.component.agenda

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.*

@Composable
fun AgendaHeader(
    modifier: Modifier = Modifier,
    title: String = "",
    subTitle: String = ""
    ) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.event),
                contentDescription = "Agenda image"
                            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(text = stringResource(R.string.event), fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.agendaTitleHeaderColor)
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                Icon(painter = painterResource(id = R.drawable.incomplete), contentDescription = "subtitle icon")

                /* TODO I had to just the padding as its aligned to the image and not the edge of the boarder */
                Spacer(modifier = Modifier.width(14.dp))

                Text(text = "Meeting", fontWeight = FontWeight.SemiBold, fontSize = 26.sp, color = MaterialTheme.colorScheme.agendaSubTitleHeaderColor)
            }
            
            Row(modifier = Modifier.weight(1F),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(painter = painterResource(id = R.drawable.forward_arrow), contentDescription = "forward arrow")
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Divider(
            modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.divider)
        )
    }
}

@Composable
@Preview(showBackground = true, name = "Task agenda header")
fun PreviewAgendaHeader() {
    BusbyTaskyTheme {
        AgendaHeader()
    }
}