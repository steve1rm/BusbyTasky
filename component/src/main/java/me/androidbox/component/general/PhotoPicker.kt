package me.androidbox.component.general

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import me.androidbox.component.ui.theme.photoTextColor

@Composable
fun PhotoPicker(modifier: Modifier = Modifier) {

    val selectedImageUri = remember {
        mutableStateListOf<Uri>()
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PHOTO", "[$uri]")
                selectedImageUri.add(uri)
            }
        })

    /* We have no images attached */
    if(selectedImageUri.isEmpty()) {
        AddFirstPhoto(modifier = modifier) {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
    else {
        AddSequentialPhoto(modifier = Modifier, selectedImageUri = selectedImageUri, onAddPhotosClicked = {
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        })
    }
}

@Composable
fun AddFirstPhoto(
    modifier: Modifier,
    onAddPhotosClicked: () -> Unit) {
    Row(modifier = modifier
        .fillMaxWidth()
        .height(100.dp)
        .background(color = MaterialTheme.colorScheme.photoBackgroundColor),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically) {

        IconButton(onClick = {
            onAddPhotosClicked()
        }) {
            Image(
                painter = painterResource(id = R.drawable.add_photo),
                contentDescription = "Add photos"
            )
        }

        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = stringResource(R.string.add_photos),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.photoTextColor
        )
    }
}

@Composable
fun AddSequentialPhoto(modifier: Modifier, selectedImageUri: SnapshotStateList<Uri>, onAddPhotosClicked: () -> Unit) {
    /* We have images that have been selected */
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Photos", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = rememberLazyListState()) {
            items(selectedImageUri) { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected image",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                IconButton(onClick = {
                    onAddPhotosClicked()
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.add_photo),
                        contentDescription = "Add photos"
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddFirstPhoto() {
    BusbyTaskyTheme {
        AddFirstPhoto(
            modifier = Modifier,
            onAddPhotosClicked = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewAddSequentialPhoto() {
    BusbyTaskyTheme {
        AddSequentialPhoto(
            modifier = Modifier,
            selectedImageUri = SnapshotStateList(),
            onAddPhotosClicked = {})
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEventPhotoPicker() {
    BusbyTaskyTheme {
        PhotoPicker()
    }
}