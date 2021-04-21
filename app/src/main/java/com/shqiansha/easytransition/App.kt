package com.shqiansha.easytransition

import android.app.Application

class App:Application(){
    override fun onCreate() {
        super.onCreate()
        ET.init(this)
    }
}