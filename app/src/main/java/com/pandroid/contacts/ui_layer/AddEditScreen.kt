package com.pandroid.contacts.ui_layer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pandroid.contacts.navigation.Routes
import com.pandroid.contacts.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditScreen(
    state: ContactState,
    navHostController: NavHostController,
    onEvent: (Boolean) -> Unit
) {


    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Add Contact",
                )
            },
            colors = topAppBarColors(
                containerColor = Color.LightGray,
                titleContentColor = Color.Black
            ),
            actions = {

            },
            navigationIcon = {
                IconButton(onClick = {

                    navHostController.popBackStack(
                        route = Routes.HomeScreen.route, // The route to pop to
                        inclusive = false // Whether to pop the destination itself
                    )

                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
        )

    }) { innerPadding ->

        var nameError by remember { mutableStateOf(false) }
        var numberError by remember { mutableStateOf(false) }
        var emailError by remember { mutableStateOf(false) }


        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.DarkGray),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(0.dp)
        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            Card(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 50.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 100.dp),
                shape = RoundedCornerShape(8),
                colors = CardDefaults.elevatedCardColors(Color.White)
            ) {

                Box(
                    modifier = Modifier.padding(top = 20.dp, start = 80.dp)
                ) {
                    RoundedImageWithIcon(
                         state = state,
                    )
                }


                OutlinedTextField(
                    value = state.name.value,
                    onValueChange = {
                        state.name.value = it
                        nameError = it.isBlank() || it.length > 25
                    },
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                    label = {
                        Text(text = "Name", color = Color.Black)
                    },
                    placeholder = {
                        Text(
                            text = "Name", color = Color.Black
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    supportingText = {
                        if (nameError) {
                            Text("Name must be between 1 and 25 characters.")
                        }
                    },
                    isError = nameError,
                    singleLine = true,
                    minLines = 1,
                    maxLines = 1,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.LightGray
                    )
                    )


                OutlinedTextField(
                    value = state.number.value,
                    onValueChange = {
                        state.number.value = it
                        numberError = it.isBlank() || it.length > 10
                    },
                    isError = numberError,
                    supportingText = {
                        if (numberError) {
                            Text("Number must be up to 10 digits.")
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Number", color = Color.Black
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.LightGray
                    ),

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null
                        )
                    },

                    label = {
                        Text(text = "Number", color = Color.Black)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    minLines = 1,
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                )


                OutlinedTextField(
                    value = state.email.value,
                    onValueChange = {
                        state.email.value = it
                        emailError = it.isBlank() || it.length > 50 ||
                                !it.contains('@') || !it.contains('.')
                    },
                    isError = emailError,
                    supportingText = {
                        if (emailError) {
                            Text("Invalid email format.")
                        }
                    },
                    placeholder = {
                        Text(
                            text = "Email", color = Color.Black
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.LightGray
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(text = "Email", color = Color.Black)
                    },
                    minLines = 1,
                    maxLines = 1,
                    singleLine = true,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                    )

                Button(
                    onClick = {
                        val isValid = !nameError && !numberError && !emailError
                        onEvent(isValid) // Pass validation result to the event handler
                    },
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    ), colors = ButtonDefaults.buttonColors(
                        containerColor = LightBlue,
                    )
                ) {
                    Text(text = "Save Contact", color = Color.White)
                }

                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}


