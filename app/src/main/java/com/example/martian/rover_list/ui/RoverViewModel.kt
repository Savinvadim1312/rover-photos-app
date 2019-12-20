package com.example.martian.rover_list.ui

import androidx.lifecycle.ViewModel
import com.example.martian.rover_list.data.RoverRepository
import javax.inject.Inject

class RoverViewModel @Inject constructor(repository: RoverRepository) : ViewModel() {
    val rovers by lazy {
        repository.rovers
    }
}