package ru.anlyashenko.fragmentapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AirplaneModeReceiver(
    private val callback: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isTurnedOn =  intent.getBooleanExtra("state", false)
            callback(isTurnedOn)
        }
    }
}