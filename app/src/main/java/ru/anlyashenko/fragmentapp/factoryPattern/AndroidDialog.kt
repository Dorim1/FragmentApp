package ru.anlyashenko.fragmentapp.factoryPattern

class AndroidDialog : Dialog() {
    override fun createButton(): Button {
        val androidButton = AndroidButton()
        return androidButton
    }
}