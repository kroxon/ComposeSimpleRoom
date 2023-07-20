package com.example.composesimpleroom

import android.content.Context
import com.example.composesimpleroom.data.Contact
import com.example.composesimpleroom.data.ContactDAO
import com.example.composesimpleroom.data.ContactDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class Repository(context: Context) : ContactDAO {
    private val dao = ContactDb.getInstance(context).contactDao()
    override suspend fun insertAll(contacts: List<Contact>) = withContext(Dispatchers.IO) {
        dao.insertAll(contacts)
    }

    override suspend fun deleteAll(contacts: List<Contact>) = withContext(Dispatchers.IO) {
        dao.deleteAll(contacts)
    }

    override suspend fun update(contact: Contact) = withContext(Dispatchers.IO) {
        dao.update(contact)
    }

    override fun getAll(): Flow<List<Contact>> {
        return dao.getAll()
    }

    override suspend fun dropDatabase() = withContext(Dispatchers.IO) {
        dao.dropDatabase()
    }
}