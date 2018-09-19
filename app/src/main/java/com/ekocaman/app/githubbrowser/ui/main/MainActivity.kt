package com.ekocaman.app.githubbrowser.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.ekocaman.app.githubbrowser.R
import com.ekocaman.app.githubbrowser.databinding.ActivityMainBinding
import com.ekocaman.app.githubbrowser.ui.base.BaseActivity
import com.ekocaman.app.githubbrowser.ui.helper.FragmentHelper
import com.ekocaman.app.githubbrowser.ui.helper.Navigator
import com.ekocaman.app.githubbrowser.ui.home.HomeFragment
import com.ekocaman.app.githubbrowser.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
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

    //    @Inject
    lateinit var fragmentHelper: FragmentHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        component.inject(this)

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

        FirebaseAuth.getInstance().currentUser?.let { currrentUser ->
            Timber.v("User : $currrentUser")
        } ?: run {
            FirebaseAuth.getInstance().signInAnonymously()
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Timber.v("Logged in successfully")
                            Timber.v("User : ${FirebaseAuth.getInstance().currentUser}")
                        } else {
                            Timber.w("Login error : ${it.exception}")
                        }
                    }
        }

//        FirebaseMessaging.getInstance().subscribeToTopic("Repositories")
//                .addOnCompleteListener { task ->
//                    var msg = "Subscriber"
//                    if (!task.isSuccessful) {
//                        msg = "Subscription failed"
//                    }
//                    Timber.v(msg)
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                }
//
//        FirebaseInstanceId.getInstance().instanceId
//                .addOnCompleteListener(OnCompleteListener { task ->
//                    if (!task.isSuccessful) {
//                        Timber.v("getInstanceId failed ${task.exception}")
//                        return@OnCompleteListener
//                    }
//
//                    // Get new Instance ID token
//                    val token = task.result.token
//
//                    // Log and toast
//                    val msg = "Token : $token"
//                    Timber.v(msg)
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                })
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
                .filter { text -> text.length > 3 }
                .subscribe { text ->
                    Timber.v("Search Text : $text")
                    viewModel.search.postValue(text)

                }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            return when (it.itemId) {
                R.id.search -> true
                else -> super.onOptionsItemSelected(item)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
