package com.ekocaman.app.githubbrowser.domain.job

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import dagger.android.AndroidInjection
import timber.log.Timber

class FirebaseJobService : JobService() {

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Timber.v("Performing long running task in scheduled job")

        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        Timber.v("Task stopped")

        return false
    }
}