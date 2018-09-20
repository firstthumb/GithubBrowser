package com.ekocaman.app.githubbrowser.domain.service

import com.ekocaman.app.githubbrowser.domain.model.FirebaseUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
        val firestore: FirebaseFirestore,
        val firebaseAuth: FirebaseAuth
) : FirebaseService {

    override fun login() {
        firebaseAuth.currentUser?.let { firebaseUser ->
            Timber.v("User : $firebaseUser")
            firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(FirebaseUserModel(userId = firebaseUser.uid, lastLogin = Date()), SetOptions.mergeFields(listOf("userId", "lastLogin")))
        } ?: run {
            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Timber.v("Logged in successfully")
                            firebaseAuth.currentUser?.let { firebaseUser ->
                                firestore.collection("users")
                                        .document(firebaseUser.uid)
                                        .set(FirebaseUserModel(userId = firebaseUser.uid, lastLogin = Date()))
                            }
                        } else {
                            Timber.w("Login error : ${it.exception}")
                        }
                    }
        }
    }

    override fun saveFirebaseMessagingToken(token: String?) {
        firebaseAuth.currentUser?.let { firebaseUser ->
            firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(mapOf(Pair("fcmToken", token)), SetOptions.merge())
        }
    }

    override fun saveUser(user: FirebaseUserModel) {
        firebaseAuth.currentUser?.let { firebaseUser ->
            firestore.collection("users")
                    .document(firebaseUser.uid)
                    .set(user, SetOptions.mergeFields(listOf("userId", "followedRepositories")))
        }
    }
}