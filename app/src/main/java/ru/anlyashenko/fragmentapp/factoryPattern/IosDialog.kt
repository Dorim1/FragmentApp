package ru.anlyashenko.fragmentapp.factoryPattern

class IosDialog : Dialog() {
    override fun createButton(): IosButton {
        val iosButton = IosButton()
        return iosButton
    }
}