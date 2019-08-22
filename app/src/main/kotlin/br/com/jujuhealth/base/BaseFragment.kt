package br.com.jujuhealth.base

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

open class BaseFragment(layout: Int) : Fragment(layout){

    lateinit var navController: NavController

}