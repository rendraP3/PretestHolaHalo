package com.dotdevs.assignment.holahalo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dotdevs.assignment.holahalo.database.model.Image

@Dao
interface ImageDao {

    @Query("SELECT * FROM image")
    fun getAll(): LiveData<List<Image>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg image: Image)

}