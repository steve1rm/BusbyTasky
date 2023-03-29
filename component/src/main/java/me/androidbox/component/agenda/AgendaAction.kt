package me.androidbox.component.agenda

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.divider
import me.androidbox.component.ui.theme.dividerBlack
import me.androidbox.component.ui.theme.visitorTextFontColor
import java.util.*

@Composable
fun AgendaAction(
    agendaActionType: AgendaActionType,
    modifier: Modifier = Modifier,
    onActionClicked: (AgendaActionType) -> Unit,
) {
    Column(modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally) {

        /* Reminders and Tasks have a top divider */
        if(agendaActionType.showDivider) {
            Divider(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.divider,
                        shape = RoundedCornerShape(8.dp)
                    ),
                thickness = 1.dp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.clickable {
                onActionClicked(agendaActionType)
            },
            text = stringResource(id = agendaActionType.titleRes).uppercase(Locale.getDefault()),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.visitorTextFontColor,
            fontWeight = FontWeight.SemiBold)

        Spacer(modifier = Modifier.height(50.dp))

        Divider(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 144.dp)
                .background(
                    color = MaterialTheme.colorScheme.dividerBlack,
                    shape = RoundedCornerShape(8.dp)
                ),
            thickness = 2.dp)
    }
}

enum class AgendaActionType(@StringRes val titleRes: Int, val showDivider: Boolean) {
    DELETE_EVENT(titleRes = R.string.delete_event, showDivider = false),
    LEAVE_EVENT(titleRes = R.string.leave_event, showDivider = false),
    JOIN_EVENT(titleRes = R.string.join_event, showDivider = false),
    DELETE_TASK(titleRes = R.string.delete_task, showDivider = true),
    DELETE_REMINDER(titleRes = R.string.delete_reminder, showDivider = true)
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaActionShowDivider() {
    BusbyTaskyTheme {
        AgendaAction(
            agendaActionType = AgendaActionType.DELETE_REMINDER,
            onActionClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaActionHideDivider() {
    BusbyTaskyTheme {
        AgendaAction(
            agendaActionType = AgendaActionType.LEAVE_EVENT,
            onActionClicked = {}
        )
    }
}