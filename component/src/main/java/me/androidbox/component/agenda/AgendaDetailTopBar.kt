package me.androidbox.component.agenda

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
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
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.backgroundBackColor
import me.androidbox.component.ui.theme.fontWhiteColor

@Composable
fun AgendaDetailTopBar(
    editModeType: EditModeType,
    displayDate: String,
    onCloseClicked: () -> Unit,
    onEditClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        IconButton(onClick = {
            onCloseClicked()
        }) {
            Icon(painterResource(id = R.drawable.close_white),
                tint = Color.White,
                contentDescription = null)
        }

        Text(
            text = displayDate.uppercase(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.fontWhiteColor)

        when (editModeType) {
            is EditModeType.EditMode -> {
                IconButton(onClick = {
                    onEditClicked()
                }) {
                    Icon(painterResource(id = editModeType.editIcon),
                        tint = Color.White,
                        contentDescription = null)
                }
            }
            is EditModeType.SaveMode -> {
                TextButton(
                    onClick = {
                        onSaveClicked()
                    }) {
                    Text(
                        text = stringResource(id = editModeType.saveRes),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.fontWhiteColor)
                }
            }
        }
    }
}

sealed interface EditModeType {
    data class EditMode(@DrawableRes val editIcon: Int = R.drawable.pencil) : EditModeType
    data class SaveMode(@StringRes val saveRes: Int = R.string.save) : EditModeType
}

@Preview(showBackground = true)
@Composable
fun PreviewAgendaDetailTopBarEdit() {
    BusbyTaskyTheme {
        AgendaDetailTopBar(
            editModeType = EditModeType.EditMode(),
            displayDate = "30 MARCH 2023",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                .padding(horizontal = 16.dp),
            onCloseClicked = { },
            onEditClicked = { },
            onSaveClicked = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAgendaDetailTopBarSave() {
    BusbyTaskyTheme {
        AgendaDetailTopBar(
            editModeType = EditModeType.SaveMode(),
            displayDate = "30 MARCH 2023",
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.backgroundBackColor)
                .padding(horizontal = 16.dp),
            onCloseClicked = { },
            onEditClicked = { },
            onSaveClicked = { }
        )
    }
}