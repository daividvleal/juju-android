package br.com.jujuhealth.features.auth.signup

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.extension.*
import br.com.jujuhealth.features.auth.HostSignActivity
import br.com.jujuhealth.widget.CustomTextView
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.progress_bar
import kotlinx.android.synthetic.main.fragment_sign_up.text_bottom
import kotlinx.android.synthetic.main.fragment_sign_up.txt_email
import kotlinx.android.synthetic.main.fragment_sign_up.txt_pwd
import java.util.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up), DatePickerDialog.OnDateSetListener{

    private lateinit var birthDay: Timestamp

    override fun onDateSet(
        view: android.widget.DatePicker?,
        year: Int,
        month: Int,
        dayOfMonth: Int
    ) {
        val calendar = Calendar.getInstance()
        calendar.set(year,month,dayOfMonth)
        birthDay = Timestamp(calendar.time)
        txt_birthday.setText(calendar.toDateDetailDialogFormat(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hostActivity = (requireActivity() as HostSignActivity)

        hostActivity.setObservable(progress_bar)
        sign_up.setOnClickListener {
            if(validateEmailAndPassword()){
                hostActivity.viewModel.signUp(txt_name.getText(), birthDay, txt_email.getText(), txt_pwd.getText())
            }
        }
        text_bottom.setTextAndMakePartClickble(getString(R.string.go_back),
            0,
            text_bottom.length()
        ){
            requireActivity().onBackPressed()
        }

        txt_birthday.setClick{
            openDatePickerDialog()
        }

    }

    private fun openDatePickerDialog(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR) - 10
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        calendar.set(year, month, day)
        val dialogDatePicker = DatePickerDialog(requireContext(),this,year,month,day)
        dialogDatePicker.datePicker.maxDate = calendar.time.time
        dialogDatePicker.show()
    }

    private fun validateEmailAndPassword(): Boolean{
        if(txt_name.getText().isEmpty()){
            txt_name.setError(CustomTextView.Companion.TYPETEXT.OBEY)
            return false
        }else if(!txt_name.getText().isName()){
            txt_name.setError(CustomTextView.Companion.TYPETEXT.INVALID)
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