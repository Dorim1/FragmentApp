package ru.anlyashenko.fragmentapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class NoticeDialogFragment : DialogFragment() {
    internal lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NoticeDialogListener
            ?: throw ClassCastException("Parent fragment must implement NoticeDialogListener")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage("This is Message")
                .setPositiveButton("Start", DialogInterface.OnClickListener { dialog, id ->
                    listener.onDialogPositiveClick(this)
                })
                .setNegativeButton("Cansel", DialogInterface.OnClickListener { dialog, id ->
                    listener.onDialogNegativeClick(this)
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}