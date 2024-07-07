package com.pandroid.contacts.ui_layer

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pandroid.contacts.navigation.Routes
import com.pandroid.contacts.ui.theme.ExtraLightBlue
import com.pandroid.contacts.ui.theme.LightBlue
import com.pandroid.contacts.ui_layer.uilogic.RandomLetterImage

@Composable
fun SpecificContactScreen(
    state: ContactState,
    id: Int?,
    name: String,
    number: String,
    email: String,
    bitmap: Bitmap?,
    dateOfCreation: Long,
    navHostController: NavHostController,
    onDismiss: () -> Unit,
    viewModel: ContactViewModel
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { /*TODO*/ },
        modifier = Modifier.height(450.dp),
        containerColor = Color.White,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { navHostController.navigate(Routes.HomeScreen.route) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = ExtraLightBlue
                    )
                }
                Text(text = name, color = ExtraLightBlue, modifier = Modifier.padding(top = 8.dp))
            }
        },
        text = {
            Card(colors = CardDefaults.cardColors(Color.White), modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.height(130.dp).fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        RandomLetterImage(text = name)
                    }
                }
                Text(
                    text = "Name = $name",
                    modifier = Modifier.padding(start = 20.dp, top = 15.dp),
                    color = Color.Black
                )
                Text(
                    text = "Number = $number",
                    modifier = Modifier.padding(start = 20.dp, top = 15.dp),
                    color = Color.Black
                )
                Text(
                    text = "Email = $email",
                    modifier = Modifier.padding(start = 20.dp, top = 15.dp),
                    color = Color.Black
                )
                Row {
                    Button(
                        onClick = {
                            state.id.value = id!!
                            state.name.value = name
                            state.number.value = number
                            state.email.value = email
                            state.dateOfCreation.value = dateOfCreation
                            viewModel.deleteContacts()

                            navHostController.popBackStack(
                                route = Routes.HomeScreen.route, // The route to pop to
                                inclusive = false // Whether to pop the destination itself
                            )
                        },
                        modifier = Modifier
                            .width(130.dp)
                            .padding(10.dp, top = 30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
                    ) {
                        Text(text = "Delete", color = Color.White)
                    }
                    Button(
                        onClick = {
                            state.id.value = id!!
                            state.name.value = name
                            state.number.value = number
                            state.email.value = email
                            state.dateOfCreation.value = dateOfCreation

                            navHostController.navigate(Routes.AddNewContact.route)
                        },
                        modifier = Modifier
                            .width(130.dp)
                            .padding(10.dp, top = 30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = LightBlue)
                    ) {
                        Text(text = "Edit", color = Color.White)
                    }
                }
            }
        }
    )
}


