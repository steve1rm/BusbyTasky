package me.androidbox.component.event

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.visitorTextFontColor

@Composable
fun VisitorSection(
    modifier: Modifier = Modifier,
    @StringRes titleResId: Int,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier
        .fillMaxWidth()) {

        Text(
            text = stringResource(id = titleResId),
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.visitorTextFontColor)

        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVisitorSection() {
    BusbyTaskyTheme {
        VisitorSection(titleResId = R.string.going) {
            Visitor(visitorInitials = "SM", visitorName = "Steve Mason") {
            }
        }
    }
}