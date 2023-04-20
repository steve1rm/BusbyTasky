package me.androidbox.presentation.photo

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import me.androidbox.component.R
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@Composable
fun PhotoScreen(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onDeletePhoto: (selectedPhoto: Uri) -> Unit,
    photoImage: Uri
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                onCloseClicked()
            }) {
                Icon(painter = painterResource(id = R.drawable.close), contentDescription = "Close button")
            }

            Text(text = "Photo")

            IconButton(onClick = {
                onDeletePhoto(photoImage)
            }) {
                Icon(painter = painterResource(id = R.drawable.bin), contentDescription = "Delete photo")
            }
        }

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = photoImage,
            contentDescription = "Photo selected",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPhotoScreen() {
    BusbyTaskyTheme {
        PhotoScreen(
            onCloseClicked = {},
            onDeletePhoto = {},
            photoImage = Uri.EMPTY
        )
    }
}