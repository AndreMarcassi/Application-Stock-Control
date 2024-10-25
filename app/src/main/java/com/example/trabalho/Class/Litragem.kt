package com.example.trabalho.Class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "litragems")
data class Litragem(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val litragem: String

){

}
