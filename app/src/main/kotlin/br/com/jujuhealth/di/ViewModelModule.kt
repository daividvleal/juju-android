package br.com.jujuhealth.di

import br.com.jujuhealth.features.auth.AuthViewModel
import br.com.jujuhealth.features.main.attendance.calendar.CalendarViewModel
import br.com.jujuhealth.features.main.changepassword.ChangePasswordViewModel
import br.com.jujuhealth.features.main.exercise.ExerciseViewModel
import br.com.jujuhealth.features.main.exercise.filter.LevelViewModel
import br.com.jujuhealth.features.main.profile.ProfileViewModel
import br.com.jujuhealth.features.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { ExerciseViewModel(get()) }
    viewModel { LevelViewModel() }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { CalendarViewModel(get()) }
    viewModel { ChangePasswordViewModel(get()) }
}