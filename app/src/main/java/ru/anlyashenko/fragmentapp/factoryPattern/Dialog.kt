package ru.anlyashenko.fragmentapp.factoryPattern

abstract class Dialog {
    abstract fun createButton() : Button
    fun renderWindow() {
        val okButton = createButton()
        okButton.render()
    }
}