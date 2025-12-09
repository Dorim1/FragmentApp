package ru.anlyashenko.fragmentapp.model

import android.util.Log
import kotlin.concurrent.Volatile

class Logger private constructor(private val tag: String) {

    companion object {
        @Volatile
        private var INSTANCE: Logger? = null

        fun getInstance(newTag: String): Logger {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

                synchronized(this) {
                    val instance = INSTANCE
                    if (instance != null) return instance

                    val created = Logger(newTag)
                    INSTANCE = created
                    return created
                }
        }
    }

    fun log(message: String) {
        Log.d(tag, "message: $message")
    }

}