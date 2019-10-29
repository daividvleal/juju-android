package br.com.jujuhealth.features.auth.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.extension.isEmail
import br.com.jujuhealth.extension.isPassword
import br.com.jujuhealth.extension.setTextAndMakePartClickble
import br.com.jujuhealth.features.auth.HostSignActivity
import br.com.jujuhealth.widget.CustomTextView
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.progress_bar
import kotlinx.android.synthetic.main.fragment_sign_up.text_bottom
import kotlinx.android.synthetic.main.fragment_sign_up.txt_email
import kotlinx.android.synthetic.main.fragment_sign_up.txt_pwd

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hostActivity = (requireActivity() as HostSignActivity)

        hostActivity.setObservable(progress_bar)
        sign_up.setOnClickListener {
            if(validateEmailAndPassword()){
                hostActivity.viewModel.signUp(txt_name.getText(), txt_birthday.getText(), txt_email.getText(), txt_pwd.getText())
            }
        }
        text_bottom.setTextAndMakePartClickble(getString(R.string.go_back),
            0,
            text_bottom.length()
        ){
            requireActivity().onBackPressed()
        }
    }

    private fun validateEmailAndPassword(): Boolean{
        if(txt_name.getText().isEmpty()){
            txt_name.setError(CustomTextView.Companion.TYPETEXT.OBEY)
            return false
        }else if(txt_birthday.getText().isEmpty()){
            txt_birthday.setError(CustomTextView.Companion.TYPETEXT.OBEY)
            return false
        }else if(!txt_email.getText().isEmail()){
            txt_email.setError(CustomTextView.Companion.TYPETEXT.EMAIL)
            return false
        }else if(!txt_pwd.getText().isPassword()){
            txt_pwd.setError(CustomTextView.Companion.TYPETEXT.PASSWORD)
            return false
        }
        return true
    }
}