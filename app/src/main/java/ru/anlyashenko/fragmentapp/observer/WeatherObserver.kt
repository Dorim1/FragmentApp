package ru.anlyashenko.fragmentapp.observer

interface WeatherObserver {

    fun update()

}

class WeatherStation {
    private val observers = mutableListOf<WeatherObserver>()
    var temperature: Int = 0
        private set

    fun subscribe(o: WeatherObserver) {
        if (!observers.contains(o)) observers.add(o)
    }

    fun unsubscribe(o: WeatherObserver) {
        observers.remove(o)
    }

    fun setTemperature(newTemp: Int) {
        temperature = newTemp
        notifyObservers()
    }

    private fun notifyObservers() {
        observers.forEach { it.update() }
    }
}