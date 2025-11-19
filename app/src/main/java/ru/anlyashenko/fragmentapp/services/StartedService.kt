package ru.anlyashenko.fragmentapp.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.res.Resources
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import ru.anlyashenko.fragmentapp.R
import kotlin.concurrent.thread

class StartedService : Service() {
    inner class LocalBinder : Binder() {
        fun getService() = this@StartedService
    }

    private val binder = LocalBinder()
    var onProgressChanged: ((Int) -> Unit)? = null
    private var serviceThread: Thread? = null
    private val notificationManager by lazy {
        getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("Service", "Сервис: onStartCommand. Поток: ${Thread.currentThread().name}")

        if (intent?.action == ACTION_STOP) {
            serviceThread?.interrupt()
            stopForeground(STOP_FOREGROUND_REMOVE)
            stopSelf()
            return START_NOT_STICKY
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                startForeground(
                    NOTIFICATION_ID,
                    createNotification(0),
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
                )
            } else {
                startForeground(NOTIFICATION_ID, createNotification(0))
            }

            serviceThread = thread {
                try {
                    for (progress in 0..100 step 10) {
                        if (Thread.currentThread().isInterrupted) {
                            break
                        } else {
                            Log.d("Service", "Прогресс: $progress")
                            notificationManager.notify(
                                NOTIFICATION_ID,
                                createNotification(progress)
                            )
                            onProgressChanged?.invoke(progress)
                            Thread.sleep(1000)
                        }

                    }
                    Log.d("Service", "Фоновая работа: ЗАВЕРШЕНА.")
                    stopForeground(STOP_FOREGROUND_REMOVE)
                    stopSelf()
                } catch (e: InterruptedException) {
                    Log.d("Service", "Поток был прерван")
                }

            }
            return START_NOT_STICKY
        }
    }

    private fun createNotification(progress: Int): Notification {
        val intent = Intent(this, StartedService::class.java)
        intent.action = ACTION_STOP
        val pendingIntent = PendingIntent.getService(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        val builder = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Загрузка данных")
            .setContentText("Пожалуйста, подождите... $progress%")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOnlyAlertOnce(true)
            .addAction(
                R.drawable.ic_launcher_foreground,
                "Остановить",
                pendingIntent
            )

        if (progress > 0) {
            builder.setProgress(100, progress, false)
        } else {
            builder.setProgress(0, 0, true)
        }

        return builder.build()
    }

    override fun onDestroy() {
        serviceThread?.interrupt()
        super.onDestroy()
    }


}

private const val ACTION_STOP = "ru.anlyashenko.stop_service"
private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "service_channel"