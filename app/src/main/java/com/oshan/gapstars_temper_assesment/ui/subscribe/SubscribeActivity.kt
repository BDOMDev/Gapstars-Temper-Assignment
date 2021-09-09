package com.oshan.gapstars_temper_assesment.ui.subscribe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oshan.gapstars_temper_assesment.R

class SubscribeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.subscribe)
    }
}