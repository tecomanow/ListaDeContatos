package br.com.mateusr.listadecontatos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.mateusr.listadecontatos.data.daos.ContactDao
import br.com.mateusr.listadecontatos.data.model.Contact

@Database (entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "contactApp_db"
                ).build()
                INSTANCE = instance
                instance
            }

        }

    }

}