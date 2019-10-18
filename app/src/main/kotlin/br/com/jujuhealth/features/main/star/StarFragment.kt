package br.com.jujuhealth.features.main.star

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import br.com.jujuhealth.R
import br.com.jujuhealth.features.main.HostMainActivity

class StarFragment  : Fragment(R.layout.fragment_star) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HostMainActivity).setUpToolbarTitle(R.string.star)
    }
}