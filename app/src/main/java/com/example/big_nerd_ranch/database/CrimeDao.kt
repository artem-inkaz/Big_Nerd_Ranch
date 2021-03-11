package com.example.big_nerd_ranch.database

import androidx.room.Dao
import androidx.room.Query
import com.example.big_nerd_ranch.model.Crime
import java.util.*
// 11.7
@Dao
interface CrimeDao {
    @Query("SELECT * FROM crime")
    fun getCrimes(): List<Crime>
    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): Crime?
}