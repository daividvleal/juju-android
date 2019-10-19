package br.com.jujuhealth.features.main.attendance.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingDiary
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import kotlinx.android.synthetic.main.loading.*
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.collections.ArrayList
import androidx.lifecycle.Observer
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.features.main.HostMainActivity
import com.google.android.material.snackbar.Snackbar

class CalendarFragmentView : Fragment(R.layout.fragment_calendar_view) {

    private val viewModel: CalendarViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
    }

    private fun setObservable() {
        viewModel.collectionAll.observe(this, Observer {
            when(it.status){
                BaseModel.Status.LOADING -> {}
                BaseModel.Status.SUCCESS -> {}
                BaseModel.Status.ERROR -> {
                    Snackbar.make(requireView(), requireContext().getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
                }
                BaseModel.Status.DEFAULT -> {}
            }
        })

        viewModel.collectionDiary.observe(this, Observer {
            when(it.status){
                BaseModel.Status.LOADING -> {}
                BaseModel.Status.SUCCESS -> {}
                BaseModel.Status.ERROR -> {
                    Snackbar.make(requireView(), requireContext().getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
                }
                BaseModel.Status.DEFAULT -> {}
            }
        })

        viewModel.diary.observe(this, Observer {
            when(it.status){
                BaseModel.Status.LOADING -> {}
                BaseModel.Status.SUCCESS -> {}
                BaseModel.Status.ERROR -> {
                    Snackbar.make(requireView(), requireContext().getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
                }
                BaseModel.Status.DEFAULT -> {}
            }
        })

        viewModel.successInserted.observe(this, Observer {
            when(it.status){
                BaseModel.Status.LOADING -> {}
                BaseModel.Status.SUCCESS -> {}
                BaseModel.Status.ERROR -> {
                    Snackbar.make(requireView(), requireContext().getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
                }
                BaseModel.Status.DEFAULT -> {}
            }
        })
    }

}