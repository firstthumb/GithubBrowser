package com.ekocaman.app.githubbrowser.domain.service

import com.ekocaman.app.githubbrowser.domain.SchedulerProvider
import com.ekocaman.app.githubbrowser.domain.model.FirebaseRepositoryModel
import com.ekocaman.app.githubbrowser.domain.model.FirebaseUserModel
import com.ekocaman.app.githubbrowser.domain.repository.GithubRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
        val firestore: FirebaseFirestore,
        val firebaseAuth: FirebaseAuth,
        val githubRepository: GithubRepository,
        val schedulerProvider: SchedulerProvider
) : UserService {

    override fun syncData() {
        // TODO: Fix Me
        firebaseAuth.currentUser?.let { firebaseUser ->
            val likedRepositories = githubRepository
                    .getLikedRepositories()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(schedulerProvider.io())
                    .blockingFirst(emptyList())

            val followedRepositories = likedRepositories.map { item ->
                FirebaseRepositoryModel(item)
            }

            val firebaseUserModel = FirebaseUserModel(userId = firebaseUser.uid, followedRepositories = followedRepositories)
            firestore.collection("users").document(firebaseUser.uid)
                    .set(firebaseUserModel, SetOptions.mergeFields("followedRepositories"))
                    .addOnSuccessListener {
                        Timber.v("Firebase sync completed successfully")
                    }
                    .addOnFailureListener {
                        Timber.w("Firebase sync failed : $it")
                    }
        }
    }
}