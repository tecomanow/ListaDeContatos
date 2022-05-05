package br.com.mateusr.listadecontatos.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import br.com.mateusr.listadecontatos.data.model.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = IGNORE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("DELETE FROM Contact")
    suspend fun deleteAll()

    @Query("SELECT * FROM Contact")
    fun getAll() : LiveData<List<Contact>>

}