package br.com.mateusr.listadecontatos.data

import br.com.mateusr.listadecontatos.data.daos.ContactDao
import br.com.mateusr.listadecontatos.data.model.Contact
import kotlinx.coroutines.coroutineScope

class ContactRepository(private val dao : ContactDao) {

    suspend fun insert(contact: Contact) = dao.insert(contact)

    suspend fun delete(contact: Contact) = dao.delete(contact)

    suspend fun update(contact: Contact) = dao.update(contact)

    suspend fun deleteAll() = dao.deleteAll()

    fun getAllContacts() = dao.getAll()

}