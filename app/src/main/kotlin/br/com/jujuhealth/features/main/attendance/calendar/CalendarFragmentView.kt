package br.com.jujuhealth.features.main.attendance.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.features.main.HostMainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import org.koin.android.ext.android.inject

class CalendarFragmentView : Fragment(R.layout.fragment_calendar_view) {

    private val viewModel: CalendarViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()

    }

    private fun setUpView(){
        if((requireActivity() as HostMainActivity).isExerciseFinished()){

        }
    }

    private fun setObservable() {
        viewModel.collectionAll.observe(this, Observer {
            setObserved(it)
        })

        viewModel.collectionDiary.observe(this, Observer {
            setObserved(it)
        })

        viewModel.diary.observe(this, Observer {
            setObserved(it)
        })

        viewModel.successInserted.observe(this, Observer {
            setObserved(it)
        })
    }

    private fun <T> setObserved(it: BaseModel<T, Exception>){
        when(it.status){
            BaseModel.Status.LOADING -> {
                loading_included.visibility = View.VISIBLE
                calendarView.visibility = View.GONE
            }
            BaseModel.Status.SUCCESS -> {
                updateView(it.data)
                loading_included.visibility = View.GONE
                calendarView.visibility = View.VISIBLE
            }
            BaseModel.Status.ERROR -> {
                loading_included.visibility = View.GONE
                calendarView.visibility = View.VISIBLE
                Snackbar.make(requireView(), requireContext().getString(R.string.error_message), Snackbar.LENGTH_SHORT).show()
            }
            BaseModel.Status.DEFAULT -> {
                loading_included.visibility = View.GONE
                calendarView.visibility = View.VISIBLE
            }
        }
    }

    private fun <T> updateView(data: T){
        when (data) {
            is List<*> -> {

            }
            is TrainingDiary -> {

            }
            is Boolean -> {

            }
        }
    }

}