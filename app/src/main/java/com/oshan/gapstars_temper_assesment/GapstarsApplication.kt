package com.oshan.gapstars_temper_assesment

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import dagger.hilt.android.HiltAndroidApp
import okhttp3.internal.Internal.instance

@HiltAndroidApp
class GapstarsApplication : Application() {

    init {
        instance = this
    }

    init {
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO)
    }

    private lateinit var appContext: Context

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {
        private var instance: GapstarsApplication? = null

        //keep relative center location in colombo as default
        var currentDeviceLocation: Location = Location("Last Known location")

        fun setCurrentLocation(location: Location) {
            currentDeviceLocation = location
        }
    }

}