package com.ekocaman.app.githubbrowser.di.components

import com.ekocaman.app.githubbrowser.GithubApp
import com.ekocaman.app.githubbrowser.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DomainModule::class,
    ApiModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    UIModule::class,
    FirebaseModule::class,
    ServicesModule::class
])
interface ApplicationComponent : AndroidInjector<GithubApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: GithubApp): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: GithubApp)
}
