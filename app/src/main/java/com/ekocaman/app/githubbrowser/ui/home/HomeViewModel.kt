package com.ekocaman.app.githubbrowser.ui.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.ekocaman.app.githubbrowser.domain.usecase.SearchRepositoryUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    val search = MutableLiveData<String>()
}