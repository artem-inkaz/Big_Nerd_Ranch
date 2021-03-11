package com.example.big_nerd_ranch.CrimeIntent

import android.content.Context
import java.lang.IllegalStateException

// инкапсуляция логики для доступа к данным
// определяет как захватывать и хранить набор данных - локально в БД или с удаленного сервера
// 11.9
// CrimeRepository это синглтон что значит в нашем процессе приложения единовременно существует
// только один его экземпляр
// Синглтон существует до тех пор пока находится в памяти и хранение в нем любых свойств позволяет получить к ним доступ
// в течении жизненного цикла activity и фрагмента
class CrimeRepository private constructor(context: Context){
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