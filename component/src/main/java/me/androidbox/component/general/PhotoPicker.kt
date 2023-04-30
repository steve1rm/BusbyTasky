package me.androidbox.component.general

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.androidbox.component.R
import me.androidbox.component.ui.theme.BusbyTaskyTheme
import me.androidbox.component.ui.theme.photoBackgroundColor
import me.androidbox.component.ui.theme.photoPickerBorderColor
import me.androidbox.component.ui.theme.photoTextColor

@Composable
fun PhotoPicker(
    listOfPhotoUri: List<String>,
    onPhotoUriSelected: (photoUri: Uri) -> Unit,
    modifier: Modifier = Modifier) {

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PHOTO", "Path of image [ $uri ]")
                onPhotoUriSelected(uri)
            }
        })

    /* We have no images attached */
    if(listOfPhotoUri.isEmpty()) {
        AddFirstPhoto(modifier = modifier) {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    else {
        AddSequentialPhoto(modifier = modifier, selectedImageUri = listOfPhotoUri, onAddPhotosClicked = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        })
    }
}

@Composable
fun AddFirstPhoto(
    modifier: Modifier,
    onAddPhotosClicked: () -> Unit) {
    Row(modifier = modifier
        .height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = {
            onAddPhotosClicked()
        }) {
            Image(
                painter = painterResource(id = R.drawable.add_photo),
                contentDescription = stringResource(id = R.string.add_photos)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = stringResource(R.string.add_photos),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.photoTextColor)
        )
    }
}

@Composable
fun AddSequentialPhoto(modifier: Modifier, selectedImageUri: List<String>, onAddPhotosClicked: () -> Unit) {
    /* We have images that have been selected */
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.photos),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 20.sp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(selectedImageUri) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = stringResource(R.string.selected_image),
                    modifier = Modifier
                        .size(60.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.photoPickerBorderColor,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .clip(shape = RoundedCornerShape(5.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                IconButton(
                    modifier = Modifier.size(60.dp),
                    onClick = {
                    onAddPhotosClicked()
                }) {
                    Image(
                        modifier = Modifier
                            .size(160.dp)
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.photoPickerBorderColor,
                                shape = RoundedCornerShape(5.dp)
                            ),
                        painter = painterResource(id = R.drawable.add_photo),
                        contentDescription = stringResource(id = R.string.add_photos)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddFirstPhoto() {
    BusbyTaskyTheme {
        AddFirstPhoto(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.photoBackgroundColor),
            onAddPhotosClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddSequentialPhoto() {
    BusbyTaskyTheme {
        AddSequentialPhoto(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.photoBackgroundColor),
            selectedImageUri = SnapshotStateList(),
            onAddPhotosClicked = {})
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEventPhotoPicker() {
    BusbyTaskyTheme {

        val listOfPhotoUri = remember {
            mutableStateListOf<String>()
        }

        PhotoPicker(
            listOfPhotoUri = listOfPhotoUri,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.photoBackgroundColor),
            onPhotoUriSelected = {})
    }
}