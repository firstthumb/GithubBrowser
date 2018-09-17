package com.ekocaman.app.githubbrowser.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    val search = MutableLiveData<String>()
}