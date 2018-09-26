package com.ekocaman.app.githubbrowser.ui.main

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.ekocaman.app.githubbrowser.R
import com.ekocaman.app.githubbrowser.databinding.ActivityMainBinding
import com.ekocaman.app.githubbrowser.domain.service.FirebaseService
import com.ekocaman.app.githubbrowser.ui.base.BaseActivity
import com.ekocaman.app.githubbrowser.ui.helper.FragmentHelper
import com.ekocaman.app.githubbrowser.ui.helper.Navigator
import com.ekocaman.app.githubbrowser.ui.home.HomeFragment
import com.ekocaman.app.githubbrowser.ui.home.HomeViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : BaseActivity() {
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    lateinit var fragmentHelper: FragmentHelper

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var firebaseService: FirebaseService

    @Inject
    lateinit var interstitialAd: InterstitialAd

    @Inject
    lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(toolbar)

        fragmentHelper = FragmentHelper(supportFragmentManager)
        fragmentHelper.replaceFragment(HomeFragment.newInstance(), R.id.mainContent, false)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW))
        }

        firebaseService.login()

        // TODO: Add proper request
        interstitialAd.loadAd(AdRequest.Builder().build())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
                .map { text -> text.toLowerCase().trim() }
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinct()
                .filter { text -> text.isNotBlank() }
                .filter { text -> text.length > 2 }
                .subscribe { text ->
                    Timber.v("Search Text : $text")
                    viewModel.search.postValue(text)

                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.SEARCH_TERM, text)
                    analytics.logEvent(FirebaseAnalytics.Event.SEARCH, bundle)
                }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            return when (it.itemId) {
                R.id.search -> true
                R.id.action_settings -> {
                    showNotificationSettings()
                    return true
                }
                R.id.action_support -> {
                    if (interstitialAd.isLoaded) {
                        interstitialAd.show()
                    } else {
                        Timber.v("Ads not loaded yet")
                    }
                    return true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotificationSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.putExtra("app_uid", applicationInfo.uid)
        intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
        intent.putExtra("app_package", packageName)
        if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        } else {
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package: $packageName")
        }
        startActivity(intent)

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "ads")
        analytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle)
    }
}
