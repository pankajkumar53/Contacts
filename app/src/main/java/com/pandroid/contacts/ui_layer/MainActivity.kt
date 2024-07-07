package com.pandroid.contacts.ui_layer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.pandroid.contacts.navigation.NavGraph
import com.pandroid.contacts.ui.theme.ContactsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val viewModel = hiltViewModel<ContactViewModel>()
            val navHostController = rememberNavController()
            val state = viewModel.state.collectAsState().value


            ContactsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    NavGraph(
                        navHostController = navHostController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding),
                        state = state
                    )
                }
            }
        }
    }
}
