package com.github.lilzulf.masaya.Util

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import androidx.fragment.app.DialogFragment


class NumberPickerDialog : DialogFragment() {
    var valueChangeListener: OnValueChangeListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val numberPicker = NumberPicker(getActivity())
        numberPicker.minValue = 2020
        numberPicker.maxValue = 2080
        val builder: AlertDialog.Builder = AlertDialog.Builder(getActivity())
        builder.setTitle("Choose Value")
        builder.setMessage("Choose a number :")
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                valueChangeListener!!.onValueChange(
                    numberPicker,
                    numberPicker.value, numberPicker.value
                )
            })
        builder.setNegativeButton("CANCEL",
            DialogInterface.OnClickListener { dialog, which ->
                valueChangeListener!!.onValueChange(
                    numberPicker,
                    numberPicker.value, numberPicker.value
                )
            })
        builder.setView(numberPicker)
        return builder.create()
    }


}