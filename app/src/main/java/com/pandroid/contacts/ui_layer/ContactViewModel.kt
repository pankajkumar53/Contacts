package com.pandroid.contacts.ui_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pandroid.contacts.data.Contact
import com.pandroid.contacts.data.ContactDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(val database: ContactDatabase) : ViewModel() {

    private var isSortedByName = MutableStateFlow(true)
    private val contact = isSortedByName.flatMapLatest {

        if (it) {
            database.dao.getContactSortByName()
        } else {
            database.dao.getContactSortByDate()
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val _state = MutableStateFlow(ContactState())

    val state = combine(_state, contact, isSortedByName) { it1, it2, it3 ->

        it1.copy(contact = it2)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ContactState()
    )


    fun changeSorting() {
        isSortedByName.value = !isSortedByName.value
    }

    fun deleteContacts() {

        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = state.value.dateOfCreation.value,
            isActive = true,
            image = state.value.image.value
        )
        viewModelScope.launch {
            database.dao.deleteContact(contact = contact)
        }

        state.value.id.value = 0
        state.value.name.value = ""
        state.value.number.value = ""
        state.value.email.value = ""
        state.value.dateOfCreation.value = 0
        state.value.image.value = ByteArray(0)

    }

    fun saveContact() {
        val contact = Contact(
            id = state.value.id.value,
            name = state.value.name.value,
            number = state.value.number.value,
            email = state.value.email.value,
            dateOfCreation = System.currentTimeMillis(),
            isActive = true,
            image = state.value.image.value
        )

        viewModelScope.launch {

            database.dao.upsertContact(contact = contact)

        }

        state.value.id.value = 0
        state.value.name.value = ""
        state.value.number.value = ""
        state.value.email.value = ""
        state.value.dateOfCreation.value = 0
        state.value.image.value=ByteArray(0)
    }

}