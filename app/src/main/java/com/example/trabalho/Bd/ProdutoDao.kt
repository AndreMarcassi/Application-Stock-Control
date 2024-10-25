package com.example.trabalho.Bd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho.Class.Litragem
import com.example.trabalho.Class.Marca
import com.example.trabalho.Class.Produto

@Dao
interface ProdutoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun AddProduto(produto: Produto)

    @Query("SELECT * FROM produtos")
    suspend fun getAllProdutos(): List<Produto>

    @Query("SELECT * FROM produtos WHERE codigo = :codigo")
    suspend fun getProdutoID(codigo: Int): Produto?

    @Update
    suspend fun updateProduto(produto: Produto)

    @Delete
    suspend fun deleteProduto(produto: Produto)
}
