package me.androidbox.component.general

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.photoBackgroundColor
import me.androidbox.component.ui.theme.photoTextColor

@Composable
fun EventPhotoPicker(modifier: Modifier = Modifier) {

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) { uri ->
        if(uri != null) {
            Log.d("PHOTO", "[$uri]")
        }
    }

    Row(modifier = modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(color = MaterialTheme.colorScheme.photoBackgroundColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = {
            activityResultLauncher.launch("image/*")
        }) {
            Image(
                painter = painterResource(id = R.drawable.add_photo),
                contentDescription = "Add photos"
            )
        }

        Spacer(modifier = Modifier.width(14.dp))
        Text(text = stringResource(R.string.add_photos),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.photoTextColor)
    }
}

@Composable
fun AddFirstPhoto() {

}

@Composable
@Preview(showBackground = true)
fun PreviewAddFirstPhoto() {
    BusbyTaskyTheme {
        AddFirstPhoto()
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEventPhotoPicker() {
    BusbyTaskyTheme {
        EventPhotoPicker()
    }
}