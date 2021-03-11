package com.example.big_nerd_ranch.CrimeIntent

import androidx.lifecycle.ViewModel
import com.example.big_nerd_ranch.model.Crime

class CrimeListViewModel: ViewModel() {

    // данный код генерирует фиктивные преступления
//    val crimes = mutableListOf<Crime>()
//
//    init {
//        for (i in 0 until 100){
//            val crime = Crime()
//            crime.title = "Crime #$i"
//            crime.isSolved = i % 2 == 0
//            crime.requiresPolice = when ((0..1).shuffled().first()) {
//                0 -> false
//                else -> true
//            }
//            crimes +=crime
//        }
//    }

    // подгружаем из репозитория
    private val crimeRepository = CrimeRepository.get()
//    val crimes = crimeRepository.getCrimes()
    val crimesListLiveData = crimeRepository.getCrimes()
}