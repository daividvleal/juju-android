package br.com.jujuhealth.features.login

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import br.com.jujuhealth.R
import br.com.jujuhealth.base.BaseFragment
import br.com.jujuhealth.extension.setTextAndMakePartClickble
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        text_bottom.setTextAndMakePartClickble(getString(R.string.dont_have_account),
            text_bottom.length() -11,
            text_bottom.length()
            ){
            navController.navigate(R.id.go_to_sign_up_from_sign_in)
        }
    }

}