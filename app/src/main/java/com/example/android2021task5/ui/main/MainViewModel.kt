package com.example.android2021task5.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android2021task5.data.models.CatImage
import com.example.android2021task5.data.repositoryes.CatImagesRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val catImagesRepository: CatImagesRepository = CatImagesRepository()
    private var catsPage = 0

    private val _catImages = MutableLiveData<List<CatImage>>()
    val catImages: LiveData<List<CatImage>> get() = _catImages

    init {
        initCatImages()
    }

    private fun initCatImages() {
        viewModelScope.launch {
            _catImages.value = catImagesRepository.getCatImages(catsPage)
        }
    }

    fun loadNextPageCatImages() {
        catsPage++
        viewModelScope.launch {

            val listCats = catImagesRepository.getCatImages(catsPage)

            val resList: MutableList<CatImage> = mutableListOf()
            resList.addAll(_catImages.value!!)
            resList.addAll(listCats)
            _catImages.value = resList
        }
    }

}
