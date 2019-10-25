package br.com.jujuhealth.features.main.changepassword

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.extension.isPassword
import br.com.jujuhealth.features.main.HostMainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_change_password.*
import org.koin.android.ext.android.inject

class ChangePasswordFragment: Fragment(R.layout.fragment_change_password){

    private lateinit var activity: HostMainActivity
    private val viewModel: ChangePasswordViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = (requireContext() as HostMainActivity)
        activity.setUpToolbarWithIconAction(R.string.change_pwd, R.drawable.ic_arrow_back){
            activity.findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        update.setOnClickListener {
            if(validateNewPwd()){
                viewModel.updatePassword(edt_pwd_actual.text.toString(), edt_pwd.text.toString())
            }
        }
        setUpObservable()
    }

    private fun setUpObservable(){
        viewModel.successUpdated.observe(viewLifecycleOwner, Observer {
            when(it.status){
                BaseModel.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                    update.isClickable = false
                }
                BaseModel.Status.ERROR -> {
                    update.isClickable = true
                    loading.visibility = View.GONE
                    val snackBar = Snackbar.make(
                        requireView(),
                        it.error?.message.toString(),
                        Snackbar.LENGTH_LONG
                    )
                    snackBar.view.background = requireContext().getDrawable(R.drawable.background_item_filter_dark)
                    snackBar.show()
                }
                BaseModel.Status.SUCCESS -> {
                    update.isClickable = true
                    loading.visibility = View.GONE
                    clearFeedback()
                }
                BaseModel.Status.DEFAULT -> {
                    update.isClickable = true
                    loading.visibility = View.GONE
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        activity.setBottomBarVisibility(false)
    }

    override fun onStop(){
        activity.setBottomBarVisibility(true)
        super.onStop()
    }

    private fun clearFeedback(){
        edt_pwd_actual.text = null
        edt_pwd.text = null
        edt_confirm_pwd.text = null
        val snackBar = Snackbar.make(
            requireView(),
            requireContext().getString(R.string.update_success),
            Snackbar.LENGTH_LONG
        )
        snackBar.view.background = requireContext().getDrawable(R.drawable.background_item_filter_dark)
        snackBar.show()
    }

    private fun validateNewPwd(): Boolean{
        if(!edt_pwd_actual.text.toString().isPassword()){
            edt_pwd_actual.error = getString(R.string.invalid_password)
            return false
        }else if(!edt_pwd.text.toString().isPassword()){
            edt_pwd.error = getString(R.string.invalid_password)
            return false
        }else if(!edt_confirm_pwd.text.toString().isPassword()){
            edt_confirm_pwd.error = getString(R.string.invalid_password)
            return false
        }else if(edt_pwd.text.toString() != edt_confirm_pwd.text.toString()){
            val snackBar = Snackbar.make(
                requireView(),
                requireContext().getString(R.string.error_message_pwd_does_not_match),
                Snackbar.LENGTH_LONG
            )
            snackBar.view.background = requireContext().getDrawable(R.drawable.background_item_filter_dark)
            snackBar.show()
            return false
        }
        return true
    }

}