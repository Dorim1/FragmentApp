package ru.anlyashenko.fragmentapp.factoryPattern

import android.util.Log

class IosButton : Button {
    override fun render() {
        Log.d("FactoryPattern", "render: IOS BUTTON")
    }
}