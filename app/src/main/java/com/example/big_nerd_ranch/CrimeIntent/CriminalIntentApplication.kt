package com.example.big_nerd_ranch.CrimeIntent

import android.app.Application
import com.example.big_nerd_ranch.model.Crime

// получить информацию о жизненном цикле приложения
class CriminalIntentApplication: Application() {

    // вызывается когда впервые загружается в память,
    // разовая инициализация
    // Экземпляр приложения не будет постоянно уничтожаться и создаваться вновь в отличие от activity
    // или фрагментов клсса
    // создается когда приложение запускается и уничтожается когда завершается процесс приложения
    override fun onCreate() {
        super.onCreate()
        // передаем экземпляр приложения в репозиторий в качестве объекта context
        CrimeRepository.initialize(this)
    }

}