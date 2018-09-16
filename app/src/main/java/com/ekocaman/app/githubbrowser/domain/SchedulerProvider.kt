package com.ekocaman.app.githubbrowser.domain

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun ui(): Scheduler

    fun io(): Scheduler
}
