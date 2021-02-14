package com.example.big_nerd_ranch.model

import androidx.annotation.StringRes

data class Question (
    @StringRes
    val textResId: Int,
    val answer: Boolean
        )