package me.androidbox.component.agenda

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import me.androidbox.component.general.TaskButton
import me.androidbox.component.ui.theme.*
import java.util.*

@Composable
fun VisitorFilter(
    modifier: Modifier = Modifier,
    selectedVisitorType: VisitorType,
    onSelectedTypeClicked: (VisitorType) -> Unit,
    onAddVisitorClicked: () -> Unit) {

    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.visitors),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.agendaBodyTextColor)

            Spacer(modifier = Modifier.width(18.dp))

            IconButton(onClick = {
                onAddVisitorClicked()
            }) {
                Image(
                    painter = painterResource(id = R.drawable.add_photo),
                    contentDescription = stringResource(id = R.string.add_photos)
                )
            }

        }

        Spacer(modifier = Modifier.height(36.dp))

        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
            VisitorType.values().forEach { visitorType ->
                val backgroundColor = getBackgroundColor(selectedVisitorType, visitorType)
                val textColor = getTextColor(selectedVisitorType, visitorType)

                TaskButton(
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .height(36.dp)
                        .width(120.dp),
                    textSize = 14.sp,
                    buttonText = stringResource(visitorType.titleRes).replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
                    },
                    onButtonClick = {
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
        MaterialTheme.colorScheme.fontWhiteColor
    } else {
        MaterialTheme.colorScheme.visitorTextFontColor
    }
}

@Composable
private fun getBackgroundColor(
    selectedVisitorType: VisitorType,
    visitorType: VisitorType
): Color {
    return if (selectedVisitorType == visitorType) {
        MaterialTheme.colorScheme.buttonColor
    } else {
        MaterialTheme.colorScheme.visitorSelectedWhiteBackgroundColor
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

    var rememberVisitorType by remember {
        mutableStateOf(VisitorType.ALL)
    }

    BusbyTaskyTheme {
        VisitorFilter(
            modifier = Modifier.fillMaxWidth(),
            selectedVisitorType = rememberVisitorType,
            onAddVisitorClicked = {},
            onSelectedTypeClicked = { visitorType ->
               rememberVisitorType = visitorType
            }
        )
    }
}