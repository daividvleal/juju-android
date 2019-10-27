package br.com.jujuhealth.features.main.attendance

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import br.com.jujuhealth.R
import br.com.jujuhealth.features.main.attendance.adapter.TabAdapter
import br.com.jujuhealth.features.main.attendance.calendar.CalendarFragment
import br.com.jujuhealth.features.main.attendance.urineloss.UrineLoss
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_attendance.*


class AttendanceFragment : Fragment(R.layout.fragment_attendance) {

    private lateinit var createView: Thread

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createView = CreateTabViews(context, tabs_view_pager, tabs, childFragmentManager)
    }

}

class CreateTabViews(
    private val context: Context?,
    private val tabs_view_pager: ViewPager,
    private val tabs: TabLayout,
    private val childFragmentManager: FragmentManager
): Thread(){

    override fun run() {
        val tabAdapter = TabAdapter(childFragmentManager)
        tabAdapter.add(CalendarFragment(), context?.getString(R.string.calendar) ?: "")
        tabAdapter.add(UrineLoss(), context?.getString(R.string.urine_loss) ?: "")
        tabs_view_pager.adapter = tabAdapter
        tabs.setupWithViewPager(tabs_view_pager)
        super.run()
    }
}