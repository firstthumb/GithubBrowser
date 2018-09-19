package com.ekocaman.app.githubbrowser

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import com.ekocaman.app.githubbrowser.di.components.DaggerApplicationComponent
import com.ekocaman.app.githubbrowser.util.LineNoDebugTree
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.reactivex.plugins.RxJavaPlugins
import timber.log.Timber


class GithubApp : DaggerApplication(), LifecycleObserver {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = appComponent

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(LineNoDebugTree())
        }

        RxJavaPlugins.setErrorHandler { e -> Timber.e(e.toString()) }

        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    private val appComponent: AndroidInjector<GithubApp> by lazy {
        val appComponent = DaggerApplicationComponent
                .builder()
                .application(this)
                .build()

        appComponent.inject(this)
        appComponent
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {

    }
}
