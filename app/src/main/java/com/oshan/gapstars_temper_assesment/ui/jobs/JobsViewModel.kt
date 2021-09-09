package com.oshan.gapstars_temper_assesment.ui.jobs

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import com.oshan.gapstars_temper_assesment.GapstarsApplication
import com.oshan.gapstars_temper_assesment.R
import com.oshan.gapstars_temper_assesment.models.Job
import com.oshan.gapstars_temper_assesment.service.repository.JobsRepo
import com.oshan.gapstars_temper_assesment.utils.ResultWrapper
import com.oshan.gapstars_temper_assesment.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(
    private val jobRepo: JobsRepo
) : ViewModel() {

    private val jobDataState: MutableLiveData<List<Job>> = MutableLiveData()
    val jobDataStateObservable: LiveData<List<Job>> get() = jobDataState

    private val loadingIndicatorState: MutableLiveData<Boolean> = MutableLiveData()
    val loadingIndicatorObservable: LiveData<Boolean> get() = loadingIndicatorState

    private val errorState: MutableLiveData<Throwable> = MutableLiveData()
    val errorStateObservable: LiveData<Throwable> get() = errorState

    private val currentDateState: MutableLiveData<String> = MutableLiveData()
    val currentDateObservable: LiveData<String> get() = currentDateState

    /**
     * Manage Subscriptions
     */
    private var compositeDisposable = CompositeDisposable()

    init {
        currentDateState.value = Utils.getCurrentDateAsString("EEEE dd MMMM")
    }

    fun getJobsData(currentDate: String): Disposable {
        loadingIndicatorState.value = true
        return jobRepo.getJobsData(currentDate)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { result ->
                loadingIndicatorState.postValue(false)
                result.let {
                    jobDataState.postValue(it)
                }
            }
            .doOnError { error ->
                loadingIndicatorState.postValue(false)
                errorState.postValue(error)
            }
            .subscribe()
    }

    /**
     * update the last known location in the Application class so that it can be used throughout the application.
     */
    fun updateLastKnownLocation(location: Location?) {
        //keep the location in the application class since this might be needed in many places of the application.
        //since the app is calculating only a rough calculation of distance. location does not need to update frequently.
        location?.let {
            GapstarsApplication.setCurrentLocation(it)
        }
    }

    /**
     * OnStop, clean up subscriptions
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        compositeDisposable.clear()
        compositeDisposable = CompositeDisposable()
    }
}

