package com.example.trabalho.Class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "marcas")
data class Marca(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val marca: String

){

}
