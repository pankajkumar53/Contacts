package com.pandroid.contacts.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "user_name") var name: String,
    var number: String,
    var email: String,
    var dateOfCreation: Long,
    var isActive: Boolean,
    val image : ByteArray? = null
)

*/

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "user_name", typeAffinity = ColumnInfo.TEXT) val name: String,
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT) val number: String,
    @ColumnInfo(typeAffinity = ColumnInfo.TEXT) val email: String,
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER) val dateOfCreation: Long,
    val isActive: Boolean = true,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) val image: ByteArray? = null
)