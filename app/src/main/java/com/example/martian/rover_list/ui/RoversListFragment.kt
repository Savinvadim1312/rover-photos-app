package com.example.martian.rover_list.ui

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.martian.R
import com.example.martian.databinding.FragmentRoversListBinding
import com.example.martian.databinding.RoverBinding
import com.example.martian.di.Injectable
import com.example.martian.di.injectViewModel
import com.example.martian.rover_list.data.RoverModel
import com.example.martian.rover_photos.ui.RoversAdapter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import com.example.martian.data.Result.Status;
import com.example.martian.ui.VerticalItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager




class RoversListFragment : Fragment(), Injectable {

    private lateinit var binding: FragmentRoversListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RoverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_rovers_list, container, false)
        context ?: return binding.root

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recycleView.layoutManager = layoutManager

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val adapter = RoversAdapter(displayMetrics.widthPixels)
        binding.recycleView.adapter = adapter

        subscribeUi(binding, adapter)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentRoversListBinding, adapter: RoversAdapter) {
        viewModel.rovers.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    binding.progressBar.isVisible = false
                    result.data?.let { adapter.submitList(it) }
                }
                Status.LOADING -> binding.progressBar.isVisible = true
                Status.ERROR -> {
                    binding.progressBar.isVisible = false;
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}
