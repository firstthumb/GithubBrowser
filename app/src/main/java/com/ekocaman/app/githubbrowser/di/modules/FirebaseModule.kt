package com.ekocaman.app.githubbrowser.di.modules

import android.content.Context
import com.ekocaman.app.githubbrowser.BuildConfig
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
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

    @Provides
    @Singleton
    internal fun provideFirebaseAdMobInterstitial(context: Context): InterstitialAd {
        if (BuildConfig.DEBUG) {
            MobileAds.initialize(context, "ca-app-pub-3940256099942544~3347511713")
        } else {
            MobileAds.initialize(context, BuildConfig.ADMOB_APP_ID)
        }

        val mInterstitialAd = InterstitialAd(context)
        if (BuildConfig.DEBUG) {
            mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        } else {
            mInterstitialAd.adUnitId = BuildConfig.ADMOB_AD_UNIT_ID
        }
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        return mInterstitialAd
    }

    @Provides
    internal fun provideFirebaseAnalytics(context: Context): FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

//    @Provides
//    internal fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

//    @Provides
//    internal fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

//    @Provides
//    internal fun provideAppAnalytics(firebaseAnalytics : FirebaseAnalytics): AppAnalytics = AppAnalytics(firebaseAnalytics)

//    @Provides
//    internal fun provideAppRemoteConfigHelper(appRemoteConfig: AppRemoteConfig): AppRemoteConfig = appRemoteConfig

//    @Provides
//    internal fun provideFirebaseCrashReportHelper(): AppCrashlytics = AppCrashlytics()

}