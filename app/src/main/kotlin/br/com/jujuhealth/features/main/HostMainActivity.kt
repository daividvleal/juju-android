package br.com.jujuhealth.features.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.jujuhealth.FIREBASE_USER
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.TrainingModel
import br.com.jujuhealth.data.model.User
import br.com.jujuhealth.features.main.changepassword.ChangePasswordFragment
import br.com.jujuhealth.features.main.exercise.filter.LevelFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main_host.*

class HostMainActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var navController: NavController
    private var exerciseFinished = false

    override fun onBackPressed() {
        toolbar?.let {
            it.background = getDrawable(R.drawable.shape_toolbar_light)
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

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        toolbar.menu?.clear()
        toolbar.navigationIcon = null
        when (menuItem.itemId) {
            R.id.navigation_calendar -> {
                toolbar.title = getString(R.string.attendance)
                navController.navigate(R.id.go_to_attendance)
            }
            R.id.navigation_video -> {
                toolbar.title = getString(R.string.video)
                navController.navigate(R.id.go_to_video)
            }
            R.id.navigation_exercise -> {
                toolbar.title = getString(R.string.exercise)
                navController.navigate(R.id.go_to_exercise)
            }
            R.id.navigation_star -> {
                toolbar.title = getString(R.string.star)
                navController.navigate(R.id.go_to_star)
            }
            R.id.navigation_profile -> {
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

    fun setUpToolbarWithIconAction(title: Int, icon: Int, action: () -> Unit) {
        setUpToolbarTitle(title)
        toolbar.navigationIcon = getDrawable(icon)
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
                    if(!isFragmentActive<LevelFragment>()){
                        navController.navigate(R.id.go_to_filter)
                        toolbar.title = getString(R.string.exercise_level)
                        toolbar.background =
                            getDrawable(R.drawable.shape_toolbar_light_no_corners)
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