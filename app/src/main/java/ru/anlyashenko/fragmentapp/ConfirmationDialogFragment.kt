package ru.anlyashenko.fragmentapp

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class ConfirmationDialogFragment : DialogFragment() {

    lateinit var listener: ConfirmationListener

    interface ConfirmationListener {
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? ConfirmationListener
            ?: throw ClassCastException("Parent fragment must implement ConfirmationListener")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Подтверждение")
                .setMessage("Вы уверены, что хотите удалить файл?")
                .setPositiveButton("Удалить", DialogInterface.OnClickListener { dialog, id ->
                    listener.onDialogPositiveClick()
                })
                .setNegativeButton("Отмена", DialogInterface.OnClickListener { dialog, id ->
                    listener.onDialogNegativeClick()
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return activity?.let {
//            val builder = AlertDialog.Builder(it)
//            val infleter = requireDialog().layoutInflater
//
//            builder.setView(infleter.inflate(R.layout.dialog_signig, null, false))
//                .setPositiveButton("Positiv", DialogInterface.OnClickListener { dialog, id ->
//                    // sign in the user
//                })
//                .setNegativeButton("Negative", DialogInterface.OnClickListener { dialog, id ->
//                    getDialog()?.cancel()
//                })
//            builder.create()
//        } ?: throw IllegalStateException("Activity cannot be null")
//    }
}