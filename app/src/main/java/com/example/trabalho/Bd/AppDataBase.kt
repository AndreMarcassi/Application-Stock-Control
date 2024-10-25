package com.example.trabalho.Bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.trabalho.Class.Litragem
import com.example.trabalho.Class.Marca
import com.example.trabalho.Class.Produto

@Database(entities = [Produto::class, Marca::class, Litragem::class], version = 4)
abstract class AppDataBase : RoomDatabase() {

    abstract fun produtoDao(): ProdutoDao
    abstract fun marcaDao(): MarcaDao
    abstract fun litragemDao(): LitragemDao

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {

            return INSTANCE ?: synchronized(this) {
                val tempInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "estoque_database"
                )
                //.fallbackToDestructiveMigration()
                .build()

                INSTANCE = tempInstance
                tempInstance
            }

        }

    }

}
