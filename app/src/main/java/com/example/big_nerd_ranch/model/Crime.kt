package com.example.big_nerd_ranch.model

import java.util.UUID
import java.util.Date

data class Crime(
        val id: UUID = UUID.randomUUID(),
        var title: String = "",
        val date: Date = Date(),
        var isSolved: Boolean = false,
        var requiresPolice: Boolean = false


)