package com.oshan.gapstars_temper_assesment.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oshan.gapstars_temper_assesment.R

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.login)


    }
}