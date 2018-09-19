package com.ekocaman.app.githubbrowser.ui.helper

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.ekocaman.app.githubbrowser.R

class FragmentHelper(private val fragmentManager: FragmentManager?) {

    @JvmOverloads
    fun replaceFragment(
            fragment: Fragment,
            containerId: Int,
            addToBackStack: Boolean = true,
            customAnimations: Int = WITHOUT_ANIMATION

    ) {
        fragmentManager?.let { fragmentManager ->
            val transaction = fragmentManager.beginTransaction()
            if (customAnimations == IN_RIGHT_OUT_LEFT) {
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            } else if (customAnimations == IN_LEFT_OUT_RIGHT) {
                transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
            }
            transaction.replace(containerId, fragment, fragment.javaClass.simpleName)
            if (addToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.commitAllowingStateLoss()
        }
    }

    companion object {
        const val IN_RIGHT_OUT_LEFT = 1
        const val IN_LEFT_OUT_RIGHT = -1
        const val WITHOUT_ANIMATION = 0
    }
}
