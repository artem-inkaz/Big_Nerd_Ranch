package com.example.big_nerd_ranch.CrimeIntent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.big_nerd_ranch.database.CrimeDatabase
import com.example.big_nerd_ranch.model.Crime
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

// инкапсуляция логики для доступа к данным
// определяет как захватывать и хранить набор данных - локально в БД или с удаленного сервера
// 11.9
// CrimeRepository это синглтон что значит в нашем процессе приложения единовременно существует
// только один его экземпляр
// Синглтон существует до тех пор пока находится в памяти и хранение в нем любых свойств позволяет
// получить к ним доступ
// в течении жизненного цикла activity и фрагмента
class CrimeRepository private constructor(context: Context){

    // свойства для хранения ссылки на БД и объекты DAO
    // создание реализации абстрактного класса CrimeDatabase
    // context т.к. БД обращается к файловой системе context нужно передавать т.к.
    // синглтон живет дольше чем классы активити
    // класс БД которую Room должен создать
    // им файла БД которую создаст Room
    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao =database.crimeDao()
    // исполнитель который ссылается на поток, возвращает экземпляр исполнителя, который указывает на новый поток
    private val executor = Executors.newSingleThreadExecutor()

    // добавляем функции в репозитоий для каждой функции в DAO
//    fun getCrimes(): List<Crime> = crimeDao.getCrimes()
//    fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)
    // добавляем LiveData
    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()
    fun getCrime(id: UUID): LiveData<Crime?> = crimeDao.getCrime(id)

    fun updateCrime(crime: Crime){
        executor.execute{
            crimeDao.updateCrime(crime)
        }
    }
    fun addCrime(crime: Crime){
        // выталкивает операции из основного потока, чтобы не блокировать работу UI потока
        executor.execute {
            crimeDao.addCrime(crime)
        }
    }

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