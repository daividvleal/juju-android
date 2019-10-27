package br.com.jujuhealth.data.notification

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters

class JujuWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): ListenableWorker.Result {
        return ListenableWorker.Result.success()
    }

}