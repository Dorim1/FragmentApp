package ru.anlyashenko.fragmentapp.model

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.selects.selectUnbiased
import ru.anlyashenko.fragmentapp.services.StartedService

class ServiceRepository(private val context: Context) {

    init {
        context.applicationContext
    }

    val progressState = MutableStateFlow(0)

    private var service: StartedService? = null
    private var isBound = false

    val connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            val binder = service as StartedService.LocalBinder
            this@ServiceRepository.service = binder.getService()

            this@ServiceRepository.service?.onProgressChanged = { newProgress ->
                progressState.value = newProgress
            }

        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service = null
        }

    }

    fun startService() {
        val intent = Intent(context, StartedService::class.java)
        ContextCompat.startForegroundService(context, intent)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        isBound = true
    }

    fun unbinding() {
        if (isBound) {
            context.unbindService(connection)
            isBound = false
            service?.onProgressChanged = null
        }
    }

    fun stopService() {
        unbinding()
        val intent = Intent(context, StartedService::class.java)
        service?.onProgressChanged = null
        context.stopService(intent)
    }

}
