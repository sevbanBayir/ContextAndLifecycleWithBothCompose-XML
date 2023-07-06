package com.sevban.contextandlifecycle

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogFragmentExample : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Sample dialog message")
            .setPositiveButton("OK") { _,_ -> }
            .create()

    companion object {
        const val TAG = "DialogFragmentExample"
    }
}