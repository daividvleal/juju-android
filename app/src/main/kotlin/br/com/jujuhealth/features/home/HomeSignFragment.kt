package br.com.jujuhealth.features.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import br.com.jujuhealth.R
import kotlinx.android.synthetic.main.fragment_home_sign.*

class HomeSignFragment : Fragment(R.layout.fragment_home_sign){

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController(view)

        sign_in.setOnClickListener {
            navController.navigate(R.id.go_to_sign_in)
        }

        sign_up.setOnClickListener {
            navController.navigate(R.id.go_to_sign_up)
        }

    }

}