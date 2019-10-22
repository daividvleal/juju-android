package br.com.jujuhealth.features.main.attendance.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.extension.getTodayFormattedKey
import br.com.jujuhealth.features.main.HostMainActivity
import com.applandeo.materialcalendarview.EventDay
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import org.koin.android.ext.android.inject
import java.util.*

class CalendarFragmentView : Fragment(R.layout.fragment_calendar_view) {

    private val viewModel: CalendarViewModel by inject()
    private var seeDetails = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
        setUpView()
    }

    private fun setUpView() {
        val activity = (requireActivity() as HostMainActivity)
        if (activity.isExerciseFinished()) {
            viewModel.getTrainingDiary(
                activity.getLoggedUser()?.uId,
                Calendar.getInstance().getTodayFormattedKey()
            )
        } else {
            viewModel.getActualMonth(activity.getLoggedUser()?.uId)
        }
    }

    private fun setObservable() {
        viewModel.collectionDiary.observe(this, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility(loadingVisibility = true)
                }
                BaseModel.Status.SUCCESS -> {
                    val events = ArrayList<EventDay>()
                    it.data?.forEach {  trainingDiary ->
                        val calendar = Calendar.getInstance()
                        calendar.time = trainingDiary.date.toDate()
                        if (trainingDiary.hasExercise() && trainingDiary.hasUrineLoss()) {
                            events.add(EventDay(calendar, R.drawable.ic_marker_and_drop_calendar))
                        } else if(trainingDiary.hasExercise()) {
                            events.add(EventDay(calendar, R.drawable.ic_marker))
                        } else if (trainingDiary.hasUrineLoss()) {
                            events.add(EventDay(calendar, R.drawable.ic_drop_yellow))
                        }
                    }
                    calendarView.setEvents(events)
                    setVisibility(calendarViewVisibility = true)
                }
                BaseModel.Status.ERROR -> {
                    setVisibility(calendarViewVisibility = true)
                    genericMessage(R.string.error_message)
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility(calendarViewVisibility = true)
                }
            }
        })

        viewModel.diary.observe(this, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility(loadingVisibility = true)
                }
                BaseModel.Status.SUCCESS -> {
                    if (seeDetails) {
                        seeDetails()
                        seeDetails = false
                    } else {
                        insert(it.data?.addTraining(activeMode))
                        val activity = (requireActivity() as HostMainActivity)
                        viewModel.getActualMonth(activity.getLoggedUser()?.uId)
                    }
                }
                BaseModel.Status.ERROR -> {
                    setVisibility(calendarViewVisibility = true)
                    genericMessage(R.string.error_message)
                }
                BaseModel.Status.DEFAULT -> {
                    insert(TrainingDiary().addTraining(activeMode))
                }
            }
        })

        viewModel.successInserted.observe(this, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility(loadingVisibility = true)
                }
                BaseModel.Status.SUCCESS -> {
                    genericMessage(R.string.congrats)
                    setVisibility(calendarViewVisibility = true)
                    val activity = (requireActivity() as HostMainActivity)
                    activity.setExerciseFinished(false)
                    viewModel.getActualMonth(activity.getLoggedUser()?.uId)
                }
                BaseModel.Status.ERROR -> {
                    setVisibility(calendarViewVisibility = true)
                    genericMessage(R.string.error_message)
                    viewModel.getActualMonth((requireActivity() as HostMainActivity).getLoggedUser()?.uId)
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility(calendarViewVisibility = true)
                }
            }
        })
    }

    private fun seeDetails() {
        //TODO SEE DETAILS
    }

    private fun insert(trainingDiary: TrainingDiary?) {
        val activity = (requireActivity() as HostMainActivity)
        viewModel.insertTraining(
            activity.getLoggedUser()?.uId,
            Calendar.getInstance().getTodayFormattedKey(),
            trainingDiary
        )
    }

    private fun genericMessage(id: Int) {
        Snackbar.make(
            requireView(),
            requireContext().getString(id),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun setVisibility(
        loadingVisibility: Boolean = false,
        calendarViewVisibility: Boolean = false
    ) {
        loading.visibility = if (loadingVisibility) {
            View.VISIBLE
        } else {
            View.GONE
        }
        calendarView.visibility = if (calendarViewVisibility) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}