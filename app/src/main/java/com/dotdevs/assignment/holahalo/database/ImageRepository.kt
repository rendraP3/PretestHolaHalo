package com.dotdevs.assignment.holahalo.database

import androidx.lifecycle.LiveData
import com.dotdevs.assignment.holahalo.database.model.Image

class ImageRepository(private val imageDao: ImageDao) {

    val allImages: LiveData<List<Image>> = imageDao.getAll()

    fun insert(image: Image) {
        imageDao.insert(image)
    }

}