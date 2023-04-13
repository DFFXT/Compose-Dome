package com.example.compose_dome

import android.app.Application
import com.example.compose_dome.skin.ContextCollector

class App : Application() {
    companion object {
        lateinit var application: Application
    }
    override fun onCreate() {
        super.onCreate()
        application = this
        val contextCollector = ContextCollector()
        registerActivityLifecycleCallbacks(contextCollector)
        ContextCollector.registerWildContext(this)
    }
}
