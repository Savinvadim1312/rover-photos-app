package com.example.martian.rover_photos.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.martian.R
import com.example.martian.databinding.FragmentRoverPhotosBinding
import com.example.martian.di.Injectable
import com.example.martian.di.injectViewModel
import com.example.martian.util.ConnectivityUtil
import javax.inject.Inject
import com.example.martian.rover_photos.ui.GridSpacingItemDecoration
/**
 * A simple [Fragment] subclass.
 */
class RoverPhotosFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RoverPhotosViewModel

    private val args: RoverPhotosFragmentArgs by navArgs()

    private lateinit var binding: FragmentRoverPhotosBinding
    private val adapter: PhotosAdapter by lazy { PhotosAdapter() }
    private lateinit var gridLayoutManager: GridLayoutManager

    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
            SPAN_COUNT,2)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context!!)
        viewModel.roverName = args.roverName


        // Inflate the layout for this fragment
        binding = FragmentRoverPhotosBinding.inflate(inflater, container, false)
        context ?: return binding.root

        gridLayoutManager = GridLayoutManager(activity,
            SPAN_COUNT
        )
        setLayoutManager()
        binding.rvPhotos.adapter = adapter
        subscribeUi(adapter)

        return binding.root
    }


    private fun subscribeUi(adapter: PhotosAdapter) {
        viewModel.photos.observe(viewLifecycleOwner) {
//            binding.progressBar.hide()
            Log.v("hello", "size " + it?.size)
            adapter.submitList(it)
        }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.rvPhotos

        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        recyclerView.addItemDecoration(gridDecoration)

        recyclerView.layoutManager = gridLayoutManager

        recyclerView.scrollToPosition(scrollPosition)
    }

    companion object {
        const val SPAN_COUNT = 2
    }

}
