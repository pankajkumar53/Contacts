package com.pandroid.contacts.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pandroid.contacts.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideDatabase(application: Application): ContactDatabase {
        return Room.databaseBuilder(
            application,
            ContactDatabase::class.java,
            "ContactApp.db"
        ).addMigrations(
            object : Migration(1, 2) { // Define Migration inline
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE `contact_table` ADD COLUMN `image` BLOB")
                }
            }
        ).build()
    }
}