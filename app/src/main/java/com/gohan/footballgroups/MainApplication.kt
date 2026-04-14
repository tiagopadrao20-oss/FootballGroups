package com.gohan.footballgroups

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application entry point.
 *
 * The [@HiltAndroidApp] annotation triggers Hilt's code generation and creates
 * the application-scoped component that serves as the root DI container.
 */
@HiltAndroidApp
class MainApplication : Application()
