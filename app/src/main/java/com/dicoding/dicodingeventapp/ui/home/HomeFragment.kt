package com.dicoding.dicodingeventapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingeventapp.R
import com.dicoding.dicodingeventapp.databinding.FragmentHomeBinding
import com.dicoding.dicodingeventapp.data.remote.response.ListEventsItem
import com.dicoding.dicodingeventapp.ui.adapter.EventAdapter
import com.dicoding.dicodingeventapp.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]

        binding.tvUpcoming.setText(R.string.title_upcoming)
        binding.tvFinished.setText(R.string.title_finished)

        mainViewModel.upcoming.observe(viewLifecycleOwner) { listEvents ->
            setUpcomingEventData(listEvents)
        }

        mainViewModel.finished.observe(viewLifecycleOwner) { listEvents ->
            setFinishedEventData(listEvents)
        }

        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpcoming.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUpcoming.addItemDecoration(itemDecoration)

        val finishedLayoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvFinished.layoutManager = finishedLayoutManager
        binding.rvFinished.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }


    private fun setUpcomingEventData(events: List<ListEventsItem>) {
        val upcomingEvents = events.take(5)
        val adapter = EventAdapter(upcomingEvents) { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        binding.rvUpcoming.adapter = adapter
    }

    private fun setFinishedEventData(events: List<ListEventsItem>) {
        val finishedEvents = events.take(5)
        val adapter = EventAdapter(finishedEvents) { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        binding.rvFinished.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarUpcoming.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.progressBarFinished.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

