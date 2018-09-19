package com.ekocaman.app.githubbrowser.ui.base

import android.support.v7.app.AppCompatDelegate
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
