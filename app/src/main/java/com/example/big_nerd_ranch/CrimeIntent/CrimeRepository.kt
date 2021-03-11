package com.example.big_nerd_ranch.CrimeIntent

import android.content.Context
import androidx.room.Room
import com.example.big_nerd_ranch.database.CrimeDatabase
import com.example.big_nerd_ranch.model.Crime
import java.lang.IllegalStateException
import java.util.*

private const val DATABASE_NAME = "crime-database"

// инкапсуляция логики для доступа к данным
// определяет как захватывать и хранить набор данных - локально в БД или с удаленного сервера
// 11.9
// CrimeRepository это синглтон что значит в нашем процессе приложения единовременно существует
// только один его экземпляр
// Синглтон существует до тех пор пока находится в памяти и хранение в нем любых свойств позволяет получить к ним доступ
// в течении жизненного цикла activity и фрагмента
class CrimeRepository private constructor(context: Context){

    // свойства для хранения ссылки на БД и объекты DAO
    // создание реализации абстрактного класса CrimeDatabase
    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext, //context т.к. БД обращается к файловой системе context нужно передавать т.к. синглтон живет дольше чем классы активити
        CrimeDatabase::class.java, // класс БД которую Room должен создать
        DATABASE_NAME // им файла БД которую создаст Room
    ).build()

    private val crimeDao =database.crimeDao()

    // добавляем функции в репозитоий для каждой функции в DAO
    fun getCrimes(): List<Crime> = crimeDao.getCrimes()
    fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)

    // 11.9
    companion object {
        private var INSTANCE: CrimeRepository? = null

        // инициализация репозитория
        fun initialize(context: Context){
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }
        // доступ к репозиторию
        fun get(): CrimeRepository{
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepositoory must be initialized")
        }
    }
}