package com.dicoding.dicodingeventapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.dicodingeventapp.R

class DetailActivity : AppCompatActivity() {

    private lateinit var eventDetailViewModel: DetailViewModel
    private lateinit var tvEventName: TextView
    private lateinit var tvOwnerName: TextView
    private lateinit var tvDescription: TextView
    private lateinit var tvBeginTime: TextView
    private lateinit var tvQuota: TextView
    private lateinit var ivEventImage: ImageView
    private lateinit var btnRegister: Button
    private lateinit var progressBar: ProgressBar
    private var eventLink: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // Menampilkan tombol back
        }

        tvEventName = findViewById(R.id.tv_detail_name)
        tvOwnerName = findViewById(R.id.tv_owner_name)
        tvDescription = findViewById(R.id.tv_detail_description)
        tvBeginTime = findViewById(R.id.tv_begin_time)
        tvQuota = findViewById(R.id.tv_quota)
        ivEventImage = findViewById(R.id.iv_detail_photo)
        btnRegister = findViewById(R.id.btn_register)
        progressBar = findViewById(R.id.progressBar)

        val eventId = intent.getIntExtra("EVENT_ID", -1)

        if (eventId != -1) {
            eventDetailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]

            eventDetailViewModel.getDetailEvent(eventId.toString())
            eventDetailViewModel.isLoading.observe(this) { isLoading ->
                showLoading(isLoading)
            }

            eventDetailViewModel.eventDetail.observe(this) { response ->
                if (response != null && !response.error) {
                    val event = response.event
                    event?.let {

                        tvEventName.text = it.name
                        tvOwnerName.text = it.ownerName
                        tvDescription.text =
                            HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                        tvBeginTime.text = it.beginTime

                        val remainingQuota = it.quota - it.registrants
                        tvQuota.text = getString(R.string.quota, it.quota, remainingQuota)

                        Glide.with(this)
                            .load(it.mediaCover)
                            .into(ivEventImage)

                        eventLink = it.link

                        btnRegister.setOnClickListener {
                            if (eventLink.isNotEmpty()) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(eventLink))
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Event link not available", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Error loading event details", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
