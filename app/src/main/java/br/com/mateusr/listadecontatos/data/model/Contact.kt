package br.com.mateusr.listadecontatos.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "codContact")
    val id : Int,
    @ColumnInfo(name = "contactName")
    var name : String,
    @ColumnInfo(name = "contactEmail")
    var email : String
)
