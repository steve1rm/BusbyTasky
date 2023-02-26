package me.androidbox.component.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.androidbox.component.R
import me.androidbox.component.general.AgendaTopAppBar
import me.androidbox.component.ui.theme.BusbyTaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            AgendaTopAppBar(
                title = stringResource(R.string.edit_description),
                onSaveClicked = {},
                onTopIconClicked = {}
            )
        }
    ) {
        TextField(modifier = Modifier
            .padding(it)
            .fillMaxSize(),
            value = "Edit your project",
            onValueChange = {

            })
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditScreen() {
    BusbyTaskyTheme {
        EditScreen()
    }
}
