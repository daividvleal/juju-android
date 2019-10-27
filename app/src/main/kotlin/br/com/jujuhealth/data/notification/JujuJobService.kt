package br.com.jujuhealth.data.notification

import android.app.job.JobParameters
import android.app.job.JobService

class JujuJobService : JobService() {

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        // TODO(developer): add long running task here.
        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }

}