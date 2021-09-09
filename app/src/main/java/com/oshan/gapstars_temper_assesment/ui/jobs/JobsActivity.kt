package com.oshan.gapstars_temper_assesment.ui.jobs

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.oshan.gapstars_temper_assesment.databinding.ActivityHomeBinding
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.oshan.gapstars_temper_assesment.R
import com.oshan.gapstars_temper_assesment.ui.base.BaseActivity
import com.oshan.gapstars_temper_assesment.ui.login.LoginActivity
import com.oshan.gapstars_temper_assesment.ui.subscribe.SubscribeActivity
import com.oshan.gapstars_temper_assesment.utils.ExceptionUtils
import com.oshan.gapstars_temper_assesment.utils.changeVisibility
import dagger.hilt.android.AndroidEntryPoint
import android.content.IntentSender
import android.os.Looper
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.oshan.gapstars_temper_assesment.utils.PermissionUtils
import com.oshan.gapstars_temper_assesment.utils.Utils


@AndroidEntryPoint
class JobsActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private val LOCATION_PERMISSION_REQUEST_CODE = 999
    private val REQUEST_CHECK_SETTINGS = 991
    private val viewModel: JobViewModel by viewModels()

    private lateinit var jobsAdapter: JobsAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback

    private var locationRequest: LocationRequest? = null

    private var requestingLocationUpdates = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //last known location is needed to calculate the distance between the user and the job
        requestLastKnownLocation()
        configureLocationListener()

        viewModel.getJobsData(Utils.getCurrentDateAsString("yyyy-MM-dd"))
    }

    override fun configViews() {
        with(viewBinding) {
            rvJobs.layoutManager = LinearLayoutManager(this@JobsActivity)
            jobsAdapter = JobsAdapter()
            rvJobs.adapter = jobsAdapter

            swipeRefresh.setOnRefreshListener {
                viewModel.getJobsData(Utils.getCurrentDateAsString("yyyy-MM-dd"))
            }
        }
    }

    override fun configClickListeners() {
        with(viewBinding){

            btnLogIn.setOnClickListener {
                startActivity(Intent(this@JobsActivity, LoginActivity::class.java))
            }

            btnSubscribe.setOnClickListener {
                startActivity(Intent(this@JobsActivity, SubscribeActivity::class.java))
            }
        }

    }

    override fun configObservables() {
        with(viewModel) {
            loadingIndicatorObservable.observe(this@JobsActivity, Observer { isLoading ->
                if (!isLoading)
                    if (viewBinding.swipeRefresh.isRefreshing) viewBinding.swipeRefresh.isRefreshing = false
                viewBinding.rvJobs.changeVisibility(visible = !isLoading)
                viewBinding.progressBar.changeVisibility(visible = isLoading)
            })

            jobDataStateObservable.observe(this@JobsActivity, Observer {
                jobsAdapter.submitData(it)
            })

            errorStateObservable.observe(this@JobsActivity, Observer { error ->
                viewBinding.errorIndicatorLayout.changeVisibility(true)

                viewBinding.txtErrorMessage.text = ExceptionUtils.getErrorMessage(this@JobsActivity, error)
            })

            currentDateObservable.observe(this@JobsActivity, Observer {
                viewBinding.txtSelectDate.text = it
            })
        }
    }

    private fun requestLastKnownLocation() {

        when  {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
                createLocationRequest()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
                PermissionUtils.showPermissionExplanation( this, "Location Permission", getString(R.string.location_permission_denied_warning)){
                    requestPermissions(
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION_REQUEST_CODE)
                }
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }



    private fun createLocationRequest() {
        locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            createLocationRequest()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this,
                        REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun configureLocationListener() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    viewModel.updateLastKnownLocation(location)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    createLocationRequest()
                } else {
                    requestLastKnownLocation()
                }
                return
            }
            REQUEST_CHECK_SETTINGS -> {

            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        //start the location updates when resuming the activity
        if (requestingLocationUpdates) startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onPause() {
        super.onPause()
        //stop location updates when pausing the activity
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}