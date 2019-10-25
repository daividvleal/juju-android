package br.com.jujuhealth.features.main.attendance.dialog.dateDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.extension.toDateDetailDialogFormat
import br.com.jujuhealth.features.main.attendance.adapter.TabAdapter
import kotlinx.android.synthetic.main.fragment_dialog_detail.*
import java.util.*

class DetailsDateDialog(private val trainingDiary: TrainingDiary?) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabAdapter = TabAdapter(childFragmentManager)
        tabAdapter.add(
            DetailsFragmentDialog(
                trainingDiary
            ), context?.getString(R.string.details) ?: ""
        )
        tabAdapter.add(
            UrineFragmentDialog(trainingDiary),
            context?.getString(R.string.urine_loss) ?: ""
        )
        tabs_view_pager_dialog.adapter = tabAdapter
        tabs_dialog.setupWithViewPager(tabs_view_pager_dialog)
        val calendar = Calendar.getInstance()
        calendar.time =
            trainingDiary?.date?.toDate()?.let {
                it
            } ?: run {
                Date()
            }
        date.text = calendar.toDateDetailDialogFormat(requireContext())

        close_dialog.setOnClickListener {
            dismiss()
        }
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}