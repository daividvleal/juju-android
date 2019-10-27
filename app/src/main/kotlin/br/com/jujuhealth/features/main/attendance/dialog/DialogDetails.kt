package br.com.jujuhealth.features.main.attendance.dialog

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
import br.com.jujuhealth.features.main.attendance.dialog.detailsExercise.DetailsExerciseFragment
import br.com.jujuhealth.features.main.attendance.dialog.detailsUrineLoss.DetailsUrineLossFragment
import kotlinx.android.synthetic.main.fragment_dialog_detail.*
import java.util.*

class DialogDetails(private val trainingDiary: TrainingDiary?) : DialogFragment() {

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
            DetailsExerciseFragment(
                trainingDiary
            ), context?.getString(R.string.series_tab_title) ?: ""
        )
        tabAdapter.add(
            DetailsUrineLossFragment(
                trainingDiary
            ),
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}