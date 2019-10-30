package br.com.jujuhealth.extension

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

//Training
const val FIREBASE_EVENT_PRESSED_SLOW_TRAIN = "pressed_slow_train"
const val FIREBASE_EVENT_PRESSED_FAST_TRAIN = "pressed_fast_train"
const val FIREBASE_EVENT_PRESSED_START_EXERCISE = "pressed_start_exercise"
const val FIREBASE_EVENT_PRESSED_STOP_EXERCISE = "pressed_stop_exercise"
const val FIREBASE_EVENT_PRESSED_RESUME_EXERCISE = "pressed_resume_exercise"

//Levels
const val FIREBASE_EVENT_PRESSED_BAR_ICON = "pressed_level_bar_icon"
const val FIREBASE_EVENT_PRESSED_EASY_LEVEL = "pressed_easy_level"
const val FIREBASE_EVENT_PRESSED_MEDIUM_LEVEL = "pressed_medium_level"
const val FIREBASE_EVENT_PRESSED_HARD_LEVEL = "pressed_hard_level"

//Calendar
const val FIREBASE_EVENT_PRESSED_CALENDAR_CELL = "pressed_calendar_cell"
const val FIREBASE_EVENT_PRESSED_ADD_URINE_LOSS = "pressed_add_urine_loss"
const val FIREBASE_EVENT_PRESSED_CONFIRM_ADD_URINE_LOSS = "pressed_confirm_add_urine"
const val FIREBASE_EVENT_PRESSED_BACK_ADD_URINE_LOSS = "pressed_back_add_urine"
const val FIREBASE_EVENT_PRESSED_SERIES_TAB_BUTTON = "pressed_series_tab_button"
const val FIREBASE_EVENT_PRESSED_URINE_TAB_BUTTON = "pressed_urine_tab_button"

//Tabs
const val FIREBASE_EVENT_PRESSED_CALENDAR_TAB_ICON_ = "pressed_calendar_tab_icon"
const val FIREBASE_EVENT_PRESSED_EXERCISE_TAB_ICON = "pressed_exercise_tab_icon"
const val FIREBASE_EVENT_PRESSED_PROFILE_TAB_ICON = "pressed_profile_tab_icon"

fun FirebaseAnalytics.log(name: String){
    val bundle = Bundle()
    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, name)
    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
    bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, name)
    logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
}