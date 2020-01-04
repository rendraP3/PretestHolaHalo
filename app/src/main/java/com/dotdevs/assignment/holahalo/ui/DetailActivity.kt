package com.dotdevs.assignment.holahalo.ui

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dotdevs.assignment.holahalo.R
import com.dotdevs.assignment.holahalo.api.model.Image
import com.dotdevs.assignment.holahalo.database.AppDatabase
import com.dotdevs.assignment.holahalo.database.ImageRepository
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var image: Image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()
        initComponentView()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun initComponentView() {
        image =  intent.getParcelableExtra("data") as Image

        userName.text = image.user
        imageResolution.text = getString(R.string.image_resolution, image.imageWidth, image.imageHeight)

        val tags = image.tags.split(",").toTypedArray()

        for (index in tags.indices) {
            val chip = Chip(chipGroup.context)
            chip.text = tags[index]

            chip.isCheckable = false
            chip.isClickable = false
            chipGroup.addView(chip)
        }

        Glide.with(this).load(image.webformatURL).into(imageView)
        Glide.with(this).load(image.userImageURL).into(userImage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_favourite) {
            val saveImage = SaveImage(image, this)
            saveImage.execute()
        }

        return super.onOptionsItemSelected(item)
    }

    class SaveImage(private val apiImage: Image, val context: Context) : AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg params: Void?): Void? {
            val dbImage = com.dotdevs.assignment.holahalo.database.model.Image(
                apiImage.id,
                apiImage.pageURL,
                apiImage.tags,
                apiImage.webformatURL,
                apiImage.imageWidth,
                apiImage.imageHeight,
                apiImage.views,
                apiImage.likes,
                apiImage.comments,
                apiImage.user,
                apiImage.userImageURL
            )

            AppDatabase.getDatabase(context).imageDao().insert(dbImage)

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Toast.makeText(context, "Image has been added to favorite", Toast.LENGTH_SHORT).show()
        }

    }
}
