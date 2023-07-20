package com.example.composesimpleroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composesimpleroom.data.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository(app.applicationContext)

    fun getContacts(): Flow<List<Contact>> {
        return repo.getAll()
    }

    fun deleteContact(contact: Contact) = CoroutineScope(viewModelScope.coroutineContext).launch {
        repo.deleteAll(listOf(contact))
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            repo.dropDatabase()
            populateDb()
        }
    }

    private fun populateDb() {
        repeat(100) {
            val time = System.currentTimeMillis()
            val contact =
                Contact(
                    name = "${time%100}",
                    surname = "${time%100}",
                    number = "$time",
                    ice = (time % 2 == 0L))
            CoroutineScope(viewModelScope.coroutineContext).launch {
                repo.insertAll(listOf(contact))
            }
        }
    }
}