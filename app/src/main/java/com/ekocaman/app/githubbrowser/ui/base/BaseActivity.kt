package com.ekocaman.app.githubbrowser.ui.base

import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import com.ekocaman.app.githubbrowser.GithubApp
import com.ekocaman.app.githubbrowser.di.components.ActivityComponent
import com.ekocaman.app.githubbrowser.di.modules.ActivityModule


abstract class BaseActivity : AppCompatActivity() {

    val component: ActivityComponent by lazy {
        (application as GithubApp).getComponent().plus(ActivityModule(this))
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
