package com.ekocaman.app.githubbrowser.ui.base

import android.support.v4.app.Fragment
import com.ekocaman.app.githubbrowser.di.modules.FragmentModule

abstract class BaseFragment : Fragment() {
    val component by lazy {
        (activity as BaseActivity).component.plus(FragmentModule(this))
    }

}
