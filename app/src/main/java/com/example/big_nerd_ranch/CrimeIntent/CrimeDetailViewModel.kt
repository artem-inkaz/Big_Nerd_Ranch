package com.example.big_nerd_ranch.CrimeIntent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.big_nerd_ranch.model.Crime
import java.util.*

class CrimeDetailViewModel: ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()
        // для сохранения объекта Crime полученного из БД
    val crimeLiveData: LiveData<Crime?> =
        Transformations.switchMap(crimeIdLiveData){ crimeId ->
            crimeRepository.getCrime(crimeId)
        }
    // чтобы ViewModel поняла какое преступление необходимо выводить
    fun loadCrime(crimeId: UUID){
        crimeIdLiveData.value = crimeId
    }
}