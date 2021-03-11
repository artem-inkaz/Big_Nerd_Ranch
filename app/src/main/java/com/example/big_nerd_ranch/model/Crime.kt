package com.example.big_nerd_ranch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID
import java.util.Date
@Entity
data class Crime(
        @PrimaryKey
        val id: UUID = UUID.randomUUID(),
        var title: String = "",
        val date: Date = Date(),
        var isSolved: Boolean = false,
        var requiresPolice: Boolean = false


)