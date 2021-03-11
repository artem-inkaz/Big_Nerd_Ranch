package com.example.big_nerd_ranch.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.big_nerd_ranch.model.Crime
import java.util.*
// 11.7
@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
//    fun getCrimes(): List<Crime>
    // LiveData нужна для упрощения передачи данных между потоками, работает в фоновом потоке, отправляет данные в основной поток
    fun getCrimes(): LiveData<List<Crime>>
    @Query("SELECT * FROM crime WHERE id=(:id)")
//    fun getCrime(id: UUID): Crime?
    fun getCrime(id: UUID): LiveData<Crime?>
}