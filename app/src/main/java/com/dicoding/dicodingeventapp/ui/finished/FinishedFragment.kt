package com.dicoding.dicodingeventapp.ui.finished

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingeventapp.databinding.FragmentFinishedBinding
import com.dicoding.dicodingeventapp.data.remote.response.ListEventsItem
import com.dicoding.dicodingeventapp.ui.adapter.EventAdapter
import com.dicoding.dicodingeventapp.ui.detail.DetailActivity

class FinishedFragment : Fragment() {

    private lateinit var binding: FragmentFinishedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FinishedViewModel::class.java]

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                searchBar.setText(query)
                searchView.hide()

                if (query.isNotEmpty()) {
                    mainViewModel.searchEvents(query)
                    Toast.makeText(requireContext(), "Mencari: $query", Toast.LENGTH_SHORT).show()
                }
                false
            }
        }

        mainViewModel.finished.observe(viewLifecycleOwner) { listEvents ->
            setEventData(listEvents)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvEvent.layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvEvent.addItemDecoration(itemDecoration)

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun setEventData(events: List<ListEventsItem>) {
        val adapter = EventAdapter(events) { event ->

            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        binding.rvEvent.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}