package com.example.big_nerd_ranch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.big_nerd_ranch.model.Crime
// 11.5
@Database (entities = [Crime::class], version = 1, exportSchema = false)
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase: RoomDatabase() {
    // подключаем DAO, ссылка на доступ чтобы получить доступ к функции интерфейса
    abstract fun crimeDao(): CrimeDao
}