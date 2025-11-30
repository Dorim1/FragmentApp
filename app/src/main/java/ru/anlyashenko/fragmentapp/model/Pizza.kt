package ru.anlyashenko.fragmentapp.model

data class Pizza (
    val size: Size,
    val cheese: Set<Cheese>,
    val pepperoni: Boolean,
) {
    class Builder (private val size: Size) {
        private var cheese: MutableSet<Cheese> = mutableSetOf()
        private var pepperoni: Boolean = false

        fun addCheese (item: Cheese) : Builder {
            this.cheese += item
            return this
        }
        // или так -> fun addCheese (item: Cheese) : Builder = apply { cheese += item }

        fun addPepperoni () : Builder = apply { pepperoni = true }

        // или так ->
//        fun addPepperoni () : Builder {
//            this.pepperoni = true
//            return this
//        }

        fun build () : Pizza {
            if (size == Size.SMALL && cheese.size > 2) throw IllegalStateException("Слишком много сыры для маленькой пиццы!")
            return Pizza(
                size,
                cheese.toSet(),
                pepperoni
            )
        }
    }
}

enum class Size {
    SMALL,
    MEDIUM,
    LARGE,
}

enum class Cheese {
    MOZZARELLA,
    PARMESAN,
    CHEDDAR,
}

val pizza = Pizza.Builder(Size.MEDIUM)
    .addCheese(Cheese.CHEDDAR)
    .addCheese(Cheese.MOZZARELLA)
    .addPepperoni()
    .build()




