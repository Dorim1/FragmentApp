package ru.anlyashenko.fragmentapp.services

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class StartedService : Service() {

    private var serviceThread: Thread? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Сервис: onStartCommand. Поток: ${Thread.currentThread().name}")
        serviceThread = thread {
            try {
                Log.d("Service", "Фоновая работа: НАЧАЛАСЬ. Поток: ${Thread.currentThread().name}")
                Thread.sleep(4000)
                Log.d("Service", "Фоновая работа: ЗАВЕРШЕНА.")
                stopSelf()
            } catch (e: InterruptedException) {
                Log.d("Service", "Поток был прерван")
            }

        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        serviceThread?.interrupt()
        super.onDestroy()
    }
}