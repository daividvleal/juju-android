package br.com.jujuhealth.features.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.jujuhealth.R
import br.com.jujuhealth.data.model.BaseModel
import br.com.jujuhealth.features.main.HostMainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HostSignActivity : AppCompatActivity() {

    val viewModel by viewModel<AuthViewModel>()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_host)
        navController = Navigation.findNavController(this, R.id.nav_sign_fragment)
    }

    fun setObservable(progressBar: View) {
        viewModel.getBaseModel()
            .observe(this, Observer {
                when (it.status) {
                    BaseModel.Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    BaseModel.Status.ERROR -> {
                        Toast.makeText(this, it.error?.message, Toast.LENGTH_LONG).show()
                        progressBar.visibility = View.GONE
                        it.status = BaseModel.Status.DEFAULT
                        it.error = null
                    }
                    BaseModel.Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        val i = Intent(this, HostMainActivity::class.java)
                        startActivity(i)
                        finish()
                    }
                    BaseModel.Status.DEFAULT -> {
                        progressBar.visibility = View.GONE
                        it.error = null
                    }
                }
            })
    }
}