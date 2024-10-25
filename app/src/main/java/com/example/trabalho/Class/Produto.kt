package com.example.trabalho.Class

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "produtos",
    foreignKeys = [
        ForeignKey(entity = Marca::class, parentColumns = ["id"], childColumns = ["marcaId"]),
        ForeignKey(entity = Litragem::class, parentColumns = ["id"], childColumns = ["litragemId"])
    ],
    indices = [Index(value = ["marcaId"]), Index(value = ["litragemId"])]
)
data class Produto (
    @PrimaryKey val codigo: Int,
    val marcaId: Int,
    val produto: String,
    val litragemId: Int,
    var quantidade: Int

){

}