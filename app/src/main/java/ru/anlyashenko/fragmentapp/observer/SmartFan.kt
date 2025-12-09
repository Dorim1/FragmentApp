package ru.anlyashenko.fragmentapp.observer

import android.util.Log

class SmartFan(private val station: WeatherStation) : WeatherObserver {
    override fun update() {
        if (station.temperature > 25) Log.d("Observer", "Жарко. Включаюсь!")
        else Log.d("Observer", "Холодно. Выключаюсь!")
    }
}