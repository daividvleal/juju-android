package br.com.jujuhealth.features.main.attendance.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_BACK_ADD_URINE_LOSS
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_CONFIRM_ADD_URINE_LOSS
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.features.main.attendance.calendar.CalendarViewModel
import kotlinx.android.synthetic.main.fragment_insert_urine_loss.*
import org.koin.android.ext.android.inject

class DialogInsertUrineLoss(private val trainingDiary: TrainingDiary?, private val update: () -> Unit) : DialogFragment(){

    private val viewModel: CalendarViewModel by inject()
    private lateinit var activityHost: HostMainActivity

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        update()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_insert_urine_loss, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activityHost = (requireActivity() as HostMainActivity)
        back.setOnClickListener {
            activityHost.log(FIREBASE_EVENT_PRESSED_BACK_ADD_URINE_LOSS)
            dismiss()
        }

        confirm.setOnClickListener {
            activityHost.log(FIREBASE_EVENT_PRESSED_CONFIRM_ADD_URINE_LOSS)
            when(radio_group.checkedRadioButtonId){
                R.id.low -> {
                    trainingDiary?.urineLoss?.add(1)
                    viewModel.updateTraining(trainingDiary)
                }
                R.id.medium -> {
                    trainingDiary?.urineLoss?.add(2)
                    viewModel.updateTraining(trainingDiary)
                }
                R.id.big -> {
                    trainingDiary?.urineLoss?.add(3)
                    viewModel.updateTraining(trainingDiary)
                }
            }
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

}