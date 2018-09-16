package com.ekocaman.app.githubbrowser.util

import timber.log.Timber

class LineNoDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(element) + ":" + element.lineNumber
    }
}