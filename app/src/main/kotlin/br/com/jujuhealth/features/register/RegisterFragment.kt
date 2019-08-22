package br.com.jujuhealth.features.register

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import br.com.jujuhealth.R
import br.com.jujuhealth.base.BaseFragment
import br.com.jujuhealth.extension.setTextAndMakePartClickble
import kotlinx.android.synthetic.main.fragment_login.*

class RegisterFragment : BaseFragment(R.layout.fragment_register) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        text_bottom.setTextAndMakePartClickble(getString(R.string.go_back),
            0,
            text_bottom.length()
        ){
            navController.navigate(R.id.go_to_sign_in_from_sign_up)
        }
    }

}