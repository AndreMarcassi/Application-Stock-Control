package com.example.trabalho.Bd

import androidx.room.*
import com.example.trabalho.Class.Litragem

@Dao
interface LitragemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddLitragem(litragem: Litragem)

    @Query("SELECT * FROM litragems")
    suspend fun getAllLitragens(): List<Litragem>

    @Query("SELECT id FROM litragems WHERE litragem = :litragem")
    suspend fun getLitragemIdByValue(litragem: String): Int?

}