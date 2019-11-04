package br.com.jujuhealth.features.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.jujuhealth.R
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_BAR_ICON
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_CALENDAR_TAB_ICON_
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_EXERCISE_TAB_ICON
import br.com.jujuhealth.extension.FIREBASE_EVENT_PRESSED_PROFILE_TAB_ICON
import br.com.jujuhealth.features.main.changepassword.ChangePasswordFragment
import br.com.jujuhealth.features.main.exercise.filter.LevelFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_host.*
import org.koin.android.ext.android.inject

class HostMainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val mainViewModel: MainViewModel by inject()
    private lateinit var navController: NavController
    private var exerciseFinished = false
    private var series = 0

    fun log(name: String){
        mainViewModel.log(name)
    }

    override fun onBackPressed() {
        toolbar?.let {
            it.background = ContextCompat.getDrawable(baseContext, R.drawable.shape_toolbar_light)
        }

        if (isFragmentActive<LevelFragment>() || isFragmentActive<ChangePasswordFragment>()){
            super.onBackPressed()
        } else {
            minimizeApp()
        }
    }

    fun setBottomBarVisibility(visible: Boolean){
        bottom_navigation.visibility = if(visible) View.VISIBLE else View.GONE
    }

    private inline fun <reified T> isFragmentActive(): Boolean{
        var result = false
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_main_fragment)
        navHostFragment?.childFragmentManager?.let {
            val fragment = it.fragments[0]
            if (fragment is T) {
                result = true
            }
        }
        return result
    }

    private fun minimizeApp() {
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_host)
        navController = Navigation.findNavController(this, R.id.nav_main_fragment)
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        bottom_navigation?.apply {
            setOnNavigationItemSelectedListener(this@HostMainActivity)
        }
        bottom_navigation.selectedItemId = R.id.navigation_exercise
    }

    fun goToCalendar(){
        bottom_navigation.selectedItemId = R.id.navigation_calendar
        navController.navigate(R.id.go_to_attendance)
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        toolbar.menu?.clear()
        toolbar.navigationIcon = null
        when (menuItem.itemId) {
            R.id.navigation_calendar -> {
                log(FIREBASE_EVENT_PRESSED_CALENDAR_TAB_ICON_)
                toolbar.title = getString(R.string.attendance)
                navController.navigate(R.id.go_to_attendance)
            }
            R.id.navigation_video -> {
                toolbar.title = getString(R.string.video)
                navController.navigate(R.id.go_to_video)
            }
            R.id.navigation_exercise -> {
                log(FIREBASE_EVENT_PRESSED_EXERCISE_TAB_ICON)
                toolbar.title = getString(R.string.exercise)
                navController.navigate(R.id.go_to_exercise)
            }
            R.id.navigation_star -> {
                toolbar.title = getString(R.string.star)
                navController.navigate(R.id.go_to_star)
            }
            R.id.navigation_profile -> {
                log(FIREBASE_EVENT_PRESSED_PROFILE_TAB_ICON)
                toolbar.title = getString(R.string.profile)
                navController.navigate(R.id.go_to_profile)
            }
        }
        return true
    }

    fun findNavController() = navController

    fun isExerciseFinished(): Boolean{
        return exerciseFinished
    }

    fun setExerciseFinished(boolean: Boolean){
        exerciseFinished = boolean
    }

    fun getSeries() = series

    fun setSeries(qtd: Int?){
        qtd?.let {
            series = qtd
        } ?: run {
            series = 0
        }
    }

    fun setNavigationIcon(icon: Int, navigate: () -> Unit){
        toolbar.navigationIcon =  ContextCompat.getDrawable(baseContext, icon)
        toolbar.setNavigationOnClickListener {
            navigate()
        }
    }

    fun setUpToolbarWithIconAction(title: Int, icon: Int, action: () -> Unit) {
        setUpToolbarTitle(title)
        toolbar.navigationIcon = ContextCompat.getDrawable(baseContext, icon)
        toolbar.setNavigationOnClickListener {
            action()
        }
        toolbar.menu?.clear()
    }

    fun setUpToolbarWithMenuItem(title: Int, menuItem: Int) {
        toolbar.navigationIcon = null
        toolbar.title = getString(title)
        if (toolbar.menu.isEmpty()) {
            toolbar.inflateMenu(menuItem)
        }
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.navigation_level -> {
                    log(FIREBASE_EVENT_PRESSED_BAR_ICON)
                    if(!isFragmentActive<LevelFragment>()){
                        navController.navigate(R.id.go_to_filter)
                        toolbar.title = getString(R.string.exercise_level)
                        toolbar.background =
                            ContextCompat.getDrawable(baseContext, R.drawable.shape_toolbar_light_no_corners)
                    }
                }
            }
            false
        }
    }

    fun setUpToolbarTitle(title: Int) {
        toolbar.menu?.clear()
        toolbar.navigationIcon = null
        toolbar.title = getString(title)
    }
}