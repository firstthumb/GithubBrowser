package com.ekocaman.app.githubbrowser

import android.app.Application
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import com.ekocaman.app.githubbrowser.di.components.ApplicationComponent
import com.ekocaman.app.githubbrowser.di.components.DaggerApplicationComponent
import com.ekocaman.app.githubbrowser.util.LineNoDebugTree
import com.orhanobut.hawk.Hawk
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber

class GithubApp : Application(), LifecycleObserver {

    private lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(LineNoDebugTree())
        }

        RxJavaPlugins.setErrorHandler { e -> Timber.e(e.toString()) }

        appComponent = DaggerApplicationComponent.builder()
                .application(this)
                .build()
        Hawk.init(this).build()

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    fun getComponent() = appComponent

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

    }
}
