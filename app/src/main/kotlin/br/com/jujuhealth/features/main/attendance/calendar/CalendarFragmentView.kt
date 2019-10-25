package br.com.jujuhealth.features.main.attendance.calendar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.activeMode
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.data.model.TrainingDiary
import br.com.jujuhealth.extension.getFormattedKey
import br.com.jujuhealth.features.main.HostMainActivity
import br.com.jujuhealth.features.main.attendance.dialog.DetailsDateDialog
import com.applandeo.materialcalendarview.EventDay
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_calendar_view.*
import org.koin.android.ext.android.inject
import java.util.*

class CalendarFragmentView : Fragment(R.layout.fragment_calendar_view) {

    private val viewModel: CalendarViewModel by inject()
    private var selectedDate: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservable()
        setUpView()
    }

    private fun setUpView() {
        if ((requireActivity() as HostMainActivity).isExerciseFinished()) {
            viewModel.getTrainingDiary(
                Calendar.getInstance().getFormattedKey()
            )
        } else {
            viewModel.getActualMonth()
        }

        calendarView.setOnDayClickListener {
            selectedDate = it.calendar.getFormattedKey()
            viewModel.getDiaryOnCalendar(selectedDate)
        }

        calendarView.setOnForwardPageChangeListener {
            viewModel.getForwardMonth()
        }

        calendarView.setOnPreviousPageChangeListener {
            viewModel.getPreviousMonth()
        }
    }

    private fun setObservable() {
        viewModel.diaryOnCalendar.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility(loadingVisibility = true)
                }
                BaseModel.Status.SUCCESS -> {
                    setVisibility(calendarViewVisibility = true)
                    seeDetails(it.data)
                }
                BaseModel.Status.ERROR -> {
                    setVisibility(calendarViewVisibility = true)
                    genericMessage(R.string.error_message)
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility(calendarViewVisibility = true)
                    val snackBar = Snackbar.make(
                        requireView(),
                        requireContext().getString(R.string.nothing_registered, selectedDate),
                        Snackbar.LENGTH_LONG
                    )
                    snackBar.view.background = requireContext().getDrawable(R.drawable.background_item_filter_dark)
                    snackBar.show()
                }
            }
        })

        viewModel.collectionDiary.observe(viewLifecycleOwner, Observer {
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
                            events.add(EventDay(calendar, R.drawable.ic_circle_drop))
                        } else if(trainingDiary.hasExercise()) {
                            events.add(EventDay(calendar, R.drawable.ic_circle_calendar))
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

        viewModel.diary.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility(loadingVisibility = true)
                }
                BaseModel.Status.SUCCESS -> {
                    insert(it.data?.addTraining(activeMode))
                    viewModel.getActualMonth()
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

        viewModel.successInserted.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    setVisibility(loadingVisibility = true)
                }
                BaseModel.Status.SUCCESS -> {
                    genericMessage(R.string.congrats)
                    setVisibility(calendarViewVisibility = true)
                    val activity = (requireActivity() as HostMainActivity)
                    activity.setExerciseFinished(false)
                    viewModel.getActualMonth()
                }
                BaseModel.Status.ERROR -> {
                    setVisibility(calendarViewVisibility = true)
                    genericMessage(R.string.error_message)
                    viewModel.getActualMonth()
                }
                BaseModel.Status.DEFAULT -> {
                    setVisibility(calendarViewVisibility = true)
                }
            }
        })
    }

    private fun seeDetails(trainingDiary: TrainingDiary?) {
        DetailsDateDialog(trainingDiary)
            .show(requireActivity().supportFragmentManager, "DIALOG")
    }

    private fun insert(trainingDiary: TrainingDiary?) {
        viewModel.insertTraining(
            Calendar.getInstance().getFormattedKey(),
            trainingDiary
        )
    }

    private fun genericMessage(id: Int) {
        val snackBar = Snackbar.make(
            requireView(),
            requireContext().getString(id),
            Snackbar.LENGTH_LONG
        )
        snackBar.view.background = requireContext().getDrawable(R.drawable.background_item_filter_dark)
        snackBar.show()
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
            add_urine_loss.visibility = View.VISIBLE
            View.VISIBLE
        } else {
            add_urine_loss.visibility = View.GONE
            View.GONE
        }
    }

}