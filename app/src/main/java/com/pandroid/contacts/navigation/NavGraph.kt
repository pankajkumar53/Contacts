package com.pandroid.contacts.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pandroid.contacts.ui_layer.AddEditScreen
import com.pandroid.contacts.ui_layer.AllContacts
import com.pandroid.contacts.ui_layer.ContactState
import com.pandroid.contacts.ui_layer.ContactViewModel

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ContactViewModel,
    state: ContactState
) {
    NavHost(navController = navHostController, startDestination = Routes.HomeScreen.route) {

        composable(Routes.HomeScreen.route) {
            AllContacts(state = state, navHostController = navHostController, viewModel = viewModel)
        }

        composable(Routes.AddNewContact.route) {
           AddEditScreen(state = state, navHostController = navHostController) {
                viewModel.saveContact()
            }
        }
    }
}