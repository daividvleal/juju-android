package br.com.jujuhealth.features.main.attendance.dialog.detailsUrineLoss

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingDiary
import kotlinx.android.synthetic.main.fragment_urine.*

class DetailsUrineLossFragment (private val trainingDiary: TrainingDiary?): Fragment(R.layout.fragment_urine){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        urine_loss_qtd.text = getString(R.string.urine_loss_qtd, trainingDiary?.getUrineLossSize())


    }

}