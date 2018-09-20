package com.ekocaman.app.githubbrowser.domain.model

import java.util.*

data class FirebaseUserModel(
        val userId: String,
        val followedRepositories: List<FirebaseRepositoryModel>? = null,
        val fcmToken: String? = null,
        val lastLogin: Date
)