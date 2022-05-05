package br.com.mateusr.listadecontatos

import android.app.Application
import br.com.mateusr.listadecontatos.data.AppDatabase
import br.com.mateusr.listadecontatos.data.ContactRepository

class App : Application() {

    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { ContactRepository(database.contactDao()) }

}