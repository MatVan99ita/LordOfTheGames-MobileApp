package com.example.lordofthegames.GridView

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.lordofthegames.R

class StartGameDialogFragment(val str: String) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(str)
                .setPositiveButton("yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        // START THE GAME!
                    })
                .setNegativeButton("but actually no",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}