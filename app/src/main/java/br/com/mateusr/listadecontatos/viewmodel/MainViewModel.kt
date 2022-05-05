package br.com.mateusr.listadecontatos.viewmodel

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.*
import br.com.mateusr.listadecontatos.data.AppDatabase
import br.com.mateusr.listadecontatos.data.ContactRepository

import br.com.mateusr.listadecontatos.data.model.Contact
import br.com.mateusr.listadecontatos.utils.Event
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.regex.Pattern

class MainViewModel(private val repository: ContactRepository) : ViewModel() {

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val deleteOrCleanButtonText = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete: Contact

    private val eventMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = eventMessage

    init {
        saveOrUpdateButtonText.value = "Salvar"
        deleteOrCleanButtonText.value = "Limpar"
    }

    private fun insert(contact: Contact) {
        viewModelScope.launch {
            repository.insert(contact)
            eventMessage.value = Event("Sucesso ao inserir contato")
        }
    }

    private fun update(contact: Contact) {
        viewModelScope.launch {
            repository.update(contact)
            eventMessage.value = Event("Sucesso ao atualizar contato")
        }
    }

    private fun delete(contact: Contact) {
        viewModelScope.launch {
            repository.delete(contact)
            eventMessage.value = Event("Sucesso ao deletar contato")
        }
    }

    private fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
            eventMessage.value = Event("Todos os contatos foram deletados")
        }
    }

    fun getAllContact(): LiveData<List<Contact>> = repository.getAllContacts()

    fun saveOrUpdateContact() {

        if(name.value == null){
            eventMessage.value = Event("Insira um nome para o contato")
        }else if(email.value == null){
            eventMessage.value = Event("Insira um email para o contato")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.value!!).matches()){
            eventMessage.value = Event("Insira um email válido")
        }else{
            if (isUpdateOrDelete) {
                contactToUpdateOrDelete.name = name.value!!
                contactToUpdateOrDelete.email = email.value!!
                update(contactToUpdateOrDelete)
            } else {
                insert(
                    Contact(
                        0,
                        name.value!!,
                        email.value!!
                    )
                )
            }

            name.value = ""
            email.value = ""

            saveOrUpdateButtonText.value = "Salvar"
            deleteOrCleanButtonText.value = "Limpar"

            isUpdateOrDelete = false
        }
    }

    fun deleteOrCleanContacts() {
        if (isUpdateOrDelete) {
            delete(contactToUpdateOrDelete)
        } else {
            deleteAll()
        }

        name.value = ""
        email.value = ""

        saveOrUpdateButtonText.value = "Salvar"
        deleteOrCleanButtonText.value = "Limpar"

        isUpdateOrDelete = false

    }

    fun setContactUpdateOrDelete(contact: Contact) {
        name.value = contact.name
        email.value = contact.email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact

        saveOrUpdateButtonText.value = "Atualizar"
        deleteOrCleanButtonText.value = "Deletar"
    }

}

class MainViewModelFactory(private val repository: ContactRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}