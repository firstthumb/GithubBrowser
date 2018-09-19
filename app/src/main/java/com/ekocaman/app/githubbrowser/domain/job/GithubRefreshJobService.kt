package com.ekocaman.app.githubbrowser.domain.job

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import timber.log.Timber

class GithubRefreshJobService : JobService() {

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Timber.v("Performing long running task in scheduled job")

        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        return false
    }
}