package br.com.jujuhealth.features.main.attendance.dialog

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import br.com.jujuhealth.R

class DialogInsertUrineLoss : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.urine_loss_dialog_title)
            builder.setItems(R.array.urine_loss_type) { _, position ->
                Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_LONG).show()
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null!")
    }

}