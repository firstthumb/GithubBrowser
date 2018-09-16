package com.ekocaman.app.githubbrowser.di.components

import com.ekocaman.app.githubbrowser.di.modules.ActivityModule
import com.ekocaman.app.githubbrowser.di.modules.FragmentModule
import com.ekocaman.app.githubbrowser.di.scopes.ActivityScope
import com.ekocaman.app.githubbrowser.ui.main.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {
    fun plus(module: FragmentModule): FragmentComponent
    fun inject(mainActivity: MainActivity)

}
