package com.oshan.gapstars_temper_assesment.utils

import android.animation.Animator
import android.animation.AnimatorSet
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.oshan.gapstars_temper_assesment.GapstarsApplication
import retrofit2.HttpException
import javax.net.ssl.HttpsURLConnection
import kotlin.math.pow
import kotlin.math.roundToInt


fun View.changeVisibility(visible: Boolean){
    visibility = if (visible)
        View.VISIBLE
    else
        View.GONE
}


