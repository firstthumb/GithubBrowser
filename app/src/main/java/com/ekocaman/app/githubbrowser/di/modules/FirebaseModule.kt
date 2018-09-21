package com.ekocaman.app.githubbrowser.di.modules

import android.content.Context
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseModule {

    @Provides
    @Singleton
    internal fun provideFirebaseDatabase(): FirebaseFirestore {
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val settings: FirebaseFirestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        firebaseFirestore.firestoreSettings = settings;
        return firebaseFirestore
    }

    @Provides
    @Singleton
    internal fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    internal fun provideFirebaseJobDispatcher(context: Context) : FirebaseJobDispatcher {
        return FirebaseJobDispatcher(GooglePlayDriver(context))
    }

//    @Provides
//    internal fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

//    @Provides
//    internal fun provideFirebaseAnalytics(): FirebaseAnalytics = FirebaseAnalytics.getInstance(application)

//    @Provides
//    internal fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

//    @Provides
//    internal fun provideAppAnalytics(firebaseAnalytics : FirebaseAnalytics): AppAnalytics = AppAnalytics(firebaseAnalytics)

//    @Provides
//    internal fun provideAppRemoteConfigHelper(appRemoteConfig: AppRemoteConfig): AppRemoteConfig = appRemoteConfig

//    @Provides
//    internal fun provideFirebaseCrashReportHelper(): AppCrashlytics = AppCrashlytics()

}