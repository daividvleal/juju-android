package br.com.jujuhealth.features.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.features.auth.HostSignActivity
import br.com.jujuhealth.features.main.HostMainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HostMainActivity).setUpToolbarTitle(R.string.profile)
        setObservable()
        setUpView()
    }

    private fun setUpView(){
        viewModel.getUser()
        signOut(logout)
        signOut(img_logout)
        navigateToChangePWd(img_change_pwd)
        navigateToChangePWd(change_pwd)
    }

    private fun navigateToChangePWd(view: View){
        view.setOnClickListener {
            (requireContext() as HostMainActivity).findNavController().navigate(R.id.go_to_change_pwd)
        }
    }

    private fun signOut(view: View) {
        view.setOnClickListener {
            viewModel.signOut()
            startActivity(Intent(requireActivity(), HostSignActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setObservable() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                BaseModel.Status.LOADING -> {
                    loading.visibility = View.VISIBLE
                    group_profile.visibility = View.GONE
                }
                BaseModel.Status.ERROR -> {
                    loading.visibility = View.GONE
                    group_profile.visibility = View.GONE
                    val snackBar = Snackbar.make(
                        requireView(),
                        requireContext().getString(R.string.error_message),
                        Snackbar.LENGTH_LONG
                    )
                    snackBar.view.background = requireContext().getDrawable(R.drawable.background_item_filter_dark)
                    snackBar.show()
                }
                BaseModel.Status.SUCCESS -> {
                    loading.visibility = View.GONE
                    group_profile.visibility = View.VISIBLE
                    txt_name.text = it.data?.name
                    txt_email.text = it.data?.email
                }
                BaseModel.Status.DEFAULT -> {
                    loading.visibility = View.GONE
                    group_profile.visibility = View.GONE
                }
            }
        })
    }

}