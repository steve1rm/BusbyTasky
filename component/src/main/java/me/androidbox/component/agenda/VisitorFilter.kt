package me.androidbox.component.agenda

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.general.TaskButton
import me.androidbox.component.ui.theme.*

@Composable
fun VisitorFilter(
    modifier: Modifier = Modifier,
    listOfVisitorType: List<VisitorType>,
    onSelectedTypeClicked: (VisitorType) -> Unit) {

    var selectedVisitorType by rememberSaveable {
        mutableStateOf(listOfVisitorType[0])
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.visitors),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.agendaBodyTextColor)

        Spacer(modifier = Modifier.height(36.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
            listOfVisitorType.forEach { visitorType ->
                val backgroundColor = getBackgroundColor(selectedVisitorType, visitorType)
                val textColor = getTextColor(selectedVisitorType, visitorType)
                
                TaskButton(
                    modifier = Modifier
                        .height(36.dp)
                        .width(120.dp),
                    textSize = 14.sp,
                    buttonText = stringResource(visitorType.titleRes),
                    onButtonClick = {
                        selectedVisitorType = visitorType
                        onSelectedTypeClicked(visitorType)
                    },
                    buttonTextColor = textColor,
                    backgroundColor = backgroundColor)
            }
        }
    }
}

@Composable
private fun getTextColor(
    selectedVisitorType: VisitorType,
    visitorType: VisitorType
) : Color {
    return if (selectedVisitorType == visitorType) {
        MaterialTheme.colorScheme.dividerBlack
    } else {
        MaterialTheme.colorScheme.backgroundWhiteColor
    }
}

@Composable
private fun getBackgroundColor(
    selectedVisitorType: VisitorType,
    visitorType: VisitorType
): Color {
    return if (selectedVisitorType == visitorType) {
        MaterialTheme.colorScheme.backgroundWhiteColor
    } else {
        MaterialTheme.colorScheme.backgroundBackColor
    }
}

enum class VisitorType(@StringRes val titleRes: Int) {
    ALL(titleRes = R.string.all),
    GOING(titleRes = R.string.going),
    NOT_GOING(titleRes = R.string.not_going)
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorFilter() {
    BusbyTaskyTheme {
        VisitorFilter(
            listOfVisitorType = VisitorType.values().toList(),
            onSelectedTypeClicked = {}
        )
    }
}