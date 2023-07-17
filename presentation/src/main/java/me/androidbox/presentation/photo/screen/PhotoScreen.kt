package me.androidbox.presentation.photo.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.androidbox.component.R
import me.androidbox.presentation.ui.theme.BusbyTaskyTheme

@Composable
fun PhotoScreen(
    photoScreenState: PhotoScreenState,
    photoScreenEvent: (PhotoScreenEvent) -> Unit,
    onCloseClicked: () -> Unit,
    onSelectedPhotoForDeletion: (photo: String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                onCloseClicked()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.close),
                    tint = Color.White,
                    contentDescription = "Close button")
            }

            Text(
                text = stringResource(me.androidbox.presentation.R.string.photo),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold)

            IconButton(onClick = {
                //  photoScreenEvent(PhotoScreenEvent.OnPhotoDelete(photoScreenState.photoSelected))
                onSelectedPhotoForDeletion(photoScreenState.photoSelected)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.bin),
                    tint = Color.White,
                    contentDescription = "Delete photo"
                )
            }
        }

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = photoScreenState.photoSelected,
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
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            photoScreenState = PhotoScreenState(),
            photoScreenEvent = {},
            onCloseClicked = {},
            onSelectedPhotoForDeletion = {}
        )
    }
}