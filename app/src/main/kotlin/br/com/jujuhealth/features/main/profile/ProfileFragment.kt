package br.com.jujuhealth.features.main.profile

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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
        arguments?.let {
            it["imageUri"]?.let { uri ->
                performCrop(uri as Uri)
            }
        }
        setObservable()
        setUpView()
    }

    private fun performCrop(picUri: Uri) {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(picUri, "image/*")
            cropIntent.putExtra("crop", true)
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("outputX", 128)
            cropIntent.putExtra("outputY", 128)
            cropIntent.putExtra("return-data", true)
            startActivityForResult(cropIntent, Companion.PIC_CROP)
        } catch (e: Exception) {
            val errorMessage = "Whoops - something went wrong!"
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.PIC_CROP) {
            if (data != null) {
                val extras = data.extras
                val selectedBitmap = extras?.getParcelable<Bitmap>("data")
                circle_profile.setImageBitmap(selectedBitmap)
            }
        }
    }

    private fun setUpView(){
        viewModel.getUser()
        signOut(logout)
        signOut(img_logout)
        navigateToChangePwd(img_change_pwd)
        navigateToChangePwd(change_pwd)
        navigateToTakePic(circle_profile)
    }

    private fun navigateToChangePwd(view: View){
        view.setOnClickListener {
            (requireContext() as HostMainActivity).findNavController().navigate(R.id.go_to_change_pwd)
        }
    }

    private fun navigateToTakePic(view: View){
        view.setOnClickListener {
            (requireContext() as HostMainActivity).findNavController().navigate(R.id.go_to_camera)
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
                    snackBar.view.background = ContextCompat.getDrawable(requireContext(), R.drawable.background_item_filter_dark)
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

    companion object {
        const val PIC_CROP = 1
    }

}