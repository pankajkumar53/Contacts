package com.pandroid.contacts.ui_layer

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pandroid.contacts.data.Contact
import com.pandroid.contacts.navigation.Routes
import com.pandroid.contacts.ui.theme.ExtraLightBlue
import com.pandroid.contacts.ui.theme.LightBlue
import com.pandroid.contacts.ui_layer.uilogic.RandomLetterImage


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AllContacts(
    state: ContactState,
    navHostController: NavHostController,
    viewModel: ContactViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.LightGray,
                    titleContentColor = Color.Black
                ),
                modifier = Modifier.clickable {
                    viewModel.changeSorting()
                },
                title = { Text(text = "All Contacts", modifier = Modifier.padding(start = 10.dp)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(Routes.AddNewContact.route)
                },
                containerColor = LightBlue,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 50.dp
                ),
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            items(state.contact) { contact ->
                var bitmap: Bitmap? = null
                if (contact.image != null)
                    bitmap = BitmapFactory.decodeByteArray(contact.image, 0, contact.image.size)

                var showDialog by remember { mutableStateOf(false) }

                Card(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 16.dp)
                        .background(Color.White),
                    shape = RoundedCornerShape(30),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(30.dp)
                ) {
                    ContactCard(
                        contact = contact,
                        bitmap = bitmap,
                        showDialog = showDialog,
                        onShowDialogChange = { showDialog = it },
                        viewModel = viewModel,
                        navHostController = navHostController,
                        state = state
                    )
                }
            }
        }
    }
}


@Composable
fun ContactCard(
    contact: Contact,
    bitmap: Bitmap?,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    viewModel: ContactViewModel,
    navHostController: NavHostController,
    state: ContactState
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        if (showDialog) {
            SpecificContactScreen(
                onDismiss = { onShowDialogChange(false) },
                id = contact.id,
                bitmap = bitmap,
                name = contact.name,
                number = contact.number,
                email = contact.email,
                dateOfCreation = contact.dateOfCreation,
                navHostController = navHostController,
                viewModel = viewModel,
                state = state
            )
        } else {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onShowDialogChange(true) }
                ) {
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        RandomLetterImage(text = contact.name)
                    }
                    Text(
                        text = contact.name,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 20.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        val context = LocalContext.current
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_CALL)
                            intent.data = Uri.parse("tel:${contact.number}")
                            context.startActivity(intent)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Call",
                                tint = ExtraLightBlue
                            )
                        }

                    }
                }
            }
        }
    }
}



