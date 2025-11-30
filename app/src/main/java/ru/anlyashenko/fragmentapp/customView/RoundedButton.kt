package ru.anlyashenko.fragmentapp.customView

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import ru.anlyashenko.fragmentapp.R

// константа для отступа в dp
private const val DEFAULT_PADDING_DP = 10
class RoundedButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
    // Значение по умолчанию
    private var cornerRadius = 0f

    // вычисление отступа в пикселях
    private val defaultPaddingPx: Int = (DEFAULT_PADDING_DP * resources.displayMetrics.density).toInt()

    init {
        // Прочитать атрибут через xml
        if (attrs != null) {
            // Получить список кастомных аттрибутов
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundedButton)
            // Достать радиус (ключ и значение по умолчанию)
            cornerRadius = typedArray.getDimension(R.styleable.RoundedButton_cornerRadius, 0f)
            // если пользователь не задал padding, то установить значение по умолчанию
            val newPaddingLeft =
                if (paddingLeft == 0) defaultPaddingPx else paddingLeft

            val newPaddingTop =
                if (paddingTop == 0) defaultPaddingPx else paddingTop

            val newPaddingRight =
                if (paddingRight == 0) defaultPaddingPx else paddingRight

            val newPaddingBottom =
                if (paddingBottom == 0) defaultPaddingPx else paddingBottom

            setPadding(newPaddingLeft, newPaddingTop, newPaddingRight, newPaddingBottom)
            // Всегда нужно освободить массив для памяти
            typedArray.recycle()
        }

        // Принять настройки
        setupBackground()
    }

    private fun setupBackground() {
        // Сохранить текущие padding из блока init
        val pLeft = paddingLeft
        val pTop = paddingTop
        val pRight = paddingRight
        val pBotton = paddingBottom

        // Создание стандартного объекта для прямоугольников с углами
        val drawable = GradientDrawable()
        // Установка радиуса из xml
        drawable.cornerRadius = cornerRadius
        // Установка цвета фона
        drawable.setColor(ContextCompat.getColor(context, R.color.black))
        // Применить этот drawable как фон кнопки
        background = drawable
        setPadding(pLeft, pTop, pRight, pBotton)
    }

    // метод, чтобы менять радиус из кода
    private fun setRadius(newRadius: Float) {
        cornerRadius = newRadius
        setupBackground() // перерисовать фон
    }

}