package com.ekocaman.app.githubbrowser.domain.service

import com.ekocaman.app.githubbrowser.domain.model.FirebaseUserModel

interface FirebaseService {

    fun login()

    fun saveFirebaseMessagingToken(token: String?)

    fun saveUser(user: FirebaseUserModel)
}