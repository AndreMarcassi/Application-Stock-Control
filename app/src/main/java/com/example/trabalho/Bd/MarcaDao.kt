package com.example.trabalho.Bd

import androidx.room.*
import com.example.trabalho.Class.Marca

@Dao
interface MarcaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddMarca(marca: Marca)

    @Query("SELECT * FROM marcas")
    suspend fun getAllMarcas(): List<Marca>

    @Query("SELECT id FROM marcas WHERE marca = :marca")
    suspend fun getMarcaIdByName(marca: String): Int?
}