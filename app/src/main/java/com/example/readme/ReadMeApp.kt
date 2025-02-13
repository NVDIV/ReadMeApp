package com.example.readme

import android.app.Application
import com.google.firebase.FirebaseApp

class ReadMeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
