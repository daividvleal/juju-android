package br.com.jujuhealth.features.main.attendance.dialog.detailsUrineLoss

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.features.main.attendance.adapter.UrineLossAdapter
import kotlinx.android.synthetic.main.fragment_urine_loss_detail.*

class DetailsUrineLossFragment (private val trainingDiary: TrainingDiary?): Fragment(R.layout.fragment_urine_loss_detail){

    private var urineLossAdapter: UrineLossAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        urine_loss_qtd.text = getString(R.string.urine_loss_qtd, trainingDiary?.urineLossSize())

        val layoutManager = LinearLayoutManager(activity)
        urine_loss_recycler_view.layoutManager = layoutManager
        urineLossAdapter = UrineLossAdapter(requireContext(), trainingDiary?.urineLoss)
        urine_loss_recycler_view.adapter = urineLossAdapter
    }

}