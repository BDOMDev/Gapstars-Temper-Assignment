package com.oshan.gapstars_temper_assesment.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint



abstract class BaseActivity<B : ViewBinding>(val bindingFactory: (LayoutInflater) -> B) : AppCompatActivity() {


    val viewBinding: B by lazy { bindingFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        configObservables()
        configClickListeners()
        configViews()
    }

    abstract fun configViews()

    abstract fun configObservables()

    abstract fun configClickListeners()

}