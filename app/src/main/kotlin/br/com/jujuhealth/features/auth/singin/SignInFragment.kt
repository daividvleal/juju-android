package br.com.jujuhealth.features.auth.singin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.extension.isEmail
import br.com.jujuhealth.extension.isPassword
import br.com.jujuhealth.extension.setTextAndMakePartClickble
import br.com.jujuhealth.features.auth.HostSignActivity
import br.com.jujuhealth.widget.CustomTextView
import kotlinx.android.synthetic.main.fragment_sign_in.*

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hostActivity = (requireActivity() as HostSignActivity)
        hostActivity.setObservable(progress_bar)

        sign_in.setOnClickListener {
            if (validateEmailAndPassword()){
                hostActivity.viewModel.signIn(txt_email.getText(), txt_pwd.getText())
            }
        }

        text_bottom.setTextAndMakePartClickble(getString(R.string.dont_have_account),
            text_bottom.length() -11,
            text_bottom.length()
            ){
            hostActivity.navController.navigate(R.id.go_to_sign_up_from_sign_in)
        }
    }

    private fun validateEmailAndPassword(): Boolean{
        if(!txt_email.getText().isEmail()){
            txt_email.setError(CustomTextView.Companion.TYPETEXT.EMAIL)
            return false
        }else if(!txt_pwd.getText().isPassword()){
            txt_pwd.setError(CustomTextView.Companion.TYPETEXT.PASSWORD)
            return false
        }
        return true
    }

}