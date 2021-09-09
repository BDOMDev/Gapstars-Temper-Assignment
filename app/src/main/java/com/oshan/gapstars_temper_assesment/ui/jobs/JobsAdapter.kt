package com.oshan.gapstars_temper_assesment.ui.jobs

import android.annotation.SuppressLint
import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oshan.gapstars_temper_assesment.GapstarsApplication
import com.oshan.gapstars_temper_assesment.databinding.RvItemJobBinding
import com.oshan.gapstars_temper_assesment.models.Job
import com.oshan.gapstars_temper_assesment.utils.Utils

class JobsAdapter : RecyclerView.Adapter<JobsAdapter.JobViewHolder>() {

    private val jobsList = ArrayList<Job>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(RvItemJobBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bind(jobsList[position])
    }

    override fun getItemCount(): Int {
        return jobsList.size
    }

    class JobViewHolder(val viewBinding: RvItemJobBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(job: Job){
            job.imageUrl?.let {
                Glide.with(viewBinding.root.context).load(it).into(viewBinding.JobImage)
            }
            viewBinding.txtJobTitle.text = job.jobTitle

            if (job.lat != null && job.long != null) {
                val jobLocation = Location("Job Location")
                jobLocation.latitude = job.lat!!
                jobLocation.longitude = job.long!!
                val distance = Utils.calculateDistanceBetweenTwoLocations(
                    GapstarsApplication.currentDeviceLocation,
                    jobLocation
                )
                viewBinding.txtCategoryAndDistance.text = "${job.categoryName} - ${String.format("%.1f", distance)} KM"
            } else {
                viewBinding.txtCategoryAndDistance.text = "${job.categoryName}"
            }

            viewBinding.txtTimeFrame.text = "${job.shiftStartFrom} - ${job.shiftStopAt}"

            viewBinding.txtJobPay.text = job.price?.let { Utils.currencyFormat(it) }

            viewBinding.txtCurrencySymbol.text = job.currencySymbol?.let { Utils.getCurrencySymbol(it) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(jobs: List<Job>) {
        jobsList.clear()
        jobsList.addAll(jobs)
        notifyDataSetChanged()
    }
}