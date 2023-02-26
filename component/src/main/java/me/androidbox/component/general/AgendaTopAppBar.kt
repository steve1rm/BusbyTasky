package me.androidbox.component.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.agendaBodyTextColor
import me.androidbox.component.ui.theme.topbarText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onTopIconClicked: () -> Unit,
    onSaveClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    style = TextStyle(textAlign = TextAlign.Center),
                    modifier = Modifier.weight(1F),
                    text = title.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.agendaBodyTextColor
                )

                TextButton(
                    onClick = {
                        onSaveClicked()
                    },
                    content = {
                        Text(
                            text = stringResource(R.string.save),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.topbarText,
                            )
                    }
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                onTopIconClicked()
            }) {
                Icon(painter = painterResource(id = R.drawable.back_arrow_black), contentDescription = "Top app bar")
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.White
        )
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewAgendaTopAppBar() {
    BusbyTaskyTheme {
        AgendaTopAppBar(
            title = stringResource(id = R.string.edit_description),
            onTopIconClicked = {},
            onSaveClicked = {})
    }
}