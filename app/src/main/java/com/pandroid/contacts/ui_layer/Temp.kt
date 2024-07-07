package com.pandroid.contacts.ui_layer

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pandroid.contacts.R
import com.pandroid.contacts.ui.theme.ExtraLightBlue
import java.io.InputStream

@Composable
fun RoundedImageWithIcon(state: ContactState) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val byte = inputStream?.readBytes()
            if (byte != null) {
                state.image.value = byte
            }
        }
    }

    val bitmap: ImageBitmap? = state.image.value?.let { byteArray ->
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        bitmap?.asImageBitmap()
    } ?: run {
        val defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.person)
        defaultBitmap.asImageBitmap()
    }

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(Color.LightGray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            tint = ExtraLightBlue,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .align(Alignment.BottomEnd)
                .clickable {
                    launcher.launch("image/*")
                }
        )
    }
}

