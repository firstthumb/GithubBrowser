package com.ekocaman.app.githubbrowser.domain.job

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.ekocaman.app.githubbrowser.domain.model.FirebaseUserModel
import com.ekocaman.app.githubbrowser.domain.service.UserService
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class GithubRefreshJobService : JobService() {

    @Inject
    internal lateinit var userService: UserService

    private var asyncTask: FirebaseSyncDataAsyncTask? = null

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onStartJob(jobParameters: JobParameters): Boolean {
        Timber.v("Performing long running task in scheduled job")

        asyncTask = FirebaseSyncDataAsyncTask(this, jobParameters, userService)
        asyncTask?.execute()

        return false
    }

    override fun onStopJob(jobParameters: JobParameters): Boolean {
        Timber.v("Task stopped")

        asyncTask?.cancel(true)
        asyncTask = null

        return false
    }

    @SuppressLint("StaticFieldLeak")
    private class FirebaseSyncDataAsyncTask constructor(
            private var jobService: JobService?,
            private var jobParameters: JobParameters?,
            private val userService: UserService
    ) : AsyncTask<FirebaseUserModel, Void, Boolean>() {

        override fun doInBackground(vararg params: FirebaseUserModel?): Boolean {
            userService.syncData()

            return true
        }

        override fun onCancelled() {
            cleanUp()
        }

        override fun onPostExecute(result: Boolean) {
            jobService?.jobFinished(jobParameters!!, !result)

            cleanUp()

            Timber.v("Async Task completed : $result")
        }

        private fun cleanUp() {
            jobService =  null
            jobParameters = null
        }
    }
}