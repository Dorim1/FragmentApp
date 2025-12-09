package ru.anlyashenko.fragmentapp.factoryPattern

import android.util.Log

class AndroidButton : Button {
    override fun render() {
        Log.d("FactoryPattern", "render: ANDROID BUTTON")
    }
}