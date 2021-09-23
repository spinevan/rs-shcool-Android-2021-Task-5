package com.example.android2021task5.data.repositoryes

import com.example.android2021task5.data.models.CatImage
import com.example.android2021task5.data.retrofit.CatAPIRetrofitClient

class CatImagesRepository {

    suspend fun getCatImages(page: Int): List<CatImage> {
        return CatAPIRetrofitClient.getCatImages(page)
    }

}