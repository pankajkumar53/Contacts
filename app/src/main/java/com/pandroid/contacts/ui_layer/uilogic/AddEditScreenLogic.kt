package com.pandroid.contacts.ui_layer.uilogic

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher

import com.pandroid.contacts.ui_layer.ContactState
import java.io.InputStream


object AddEditScreenLogic {
    object AddEditContactHelper {

        // Validation function
        fun validateInput(name: String, number: String, email: String): Boolean {
            val nameError = name.isBlank() || name.length > 25
            val numberError = number.isBlank() || number.length > 10
            val emailError = email.isBlank() || email.length > 50 ||
                    !email.contains('@') || !email.contains('.')

            return !nameError && !numberError && !emailError
        }
    }
}



