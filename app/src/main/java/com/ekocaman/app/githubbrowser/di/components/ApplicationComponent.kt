package com.ekocaman.app.githubbrowser.di.components

import android.app.Application
import com.ekocaman.app.githubbrowser.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationModule::class,
    DomainModule::class,
    ApiModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {
    fun plus(activityModule: ActivityModule): ActivityComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}
