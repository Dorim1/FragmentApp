package ru.anlyashenko.fragmentapp.observer

import android.util.Log

class PhoneDisplay(private val station: WeatherStation) : WeatherObserver {
    override fun update() {
        Log.d("Observer", "Телефон показывает +${station.temperature}")
    }
}