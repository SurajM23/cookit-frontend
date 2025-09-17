package com.example.cookit

import android.app.Application
import com.example.cookit.utils.PrefManager

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize PrefManager or other components if needed
        PrefManager.init(this)
    }
}