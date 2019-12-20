package com.example.martian.photo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs

import com.example.martian.R
import com.example.martian.databinding.FragmentPhotoBinding
import com.example.martian.di.Injectable
import com.example.martian.di.injectViewModel
import com.example.martian.rover_photos.data.PhotoModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.rover.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class PhotoFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PhotoViewModel

    private lateinit var binding: FragmentPhotoBinding
    private val args: PhotoFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        viewModel.id = args.photoId

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_photo, container, false)

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentPhotoBinding) {
        viewModel.photo.observe(viewLifecycleOwner, Observer { result ->
            result?.let { bindView(binding, it) }
        })
    }


    private fun bindView(binding: FragmentPhotoBinding, photo: PhotoModel) {
        photo.apply {
            binding.camera.text = camera.name
            binding.date.text = date
            val picasso = Picasso.get()
            picasso.load(imgSrc.replace("http", "https")).into(binding.image)
        }
    }
}
