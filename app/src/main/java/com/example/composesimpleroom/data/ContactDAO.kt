package com.example.composesimpleroom.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDAO {

    @Insert
    suspend fun insertAll(contacts: List<Contact>)

    @Delete
    suspend fun deleteAll(contacts: List<Contact>)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contact_table")
    fun getAll(): Flow<List<Contact>>

    @Query("DELETE FROM contact_table")
    suspend fun dropDatabase()

}