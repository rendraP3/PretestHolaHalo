package com.dotdevs.assignment.holahalo.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dotdevs.assignment.holahalo.R
import com.dotdevs.assignment.holahalo.adapter.ImageLocalAdapter
import com.dotdevs.assignment.holahalo.database.AppDatabase
import com.dotdevs.assignment.holahalo.database.ImageRepository
import kotlinx.android.synthetic.main.fragment_layout.*

/**
 * A simple [Fragment] subclass.
 */
class DatabaseFragment : Fragment() {

    private lateinit var repository: ImageRepository
    private lateinit var adapter: ImageLocalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initRecyclerView()
        fetchImage()
    }

    private fun fetchImage() {
        val imageDao = AppDatabase.getDatabase(activity!!.application).imageDao()
        repository = ImageRepository(imageDao)

        repository.allImages.observe(this, Observer { images ->
            images?.let { adapter.addImages(it) }
        })
    }

    private fun initRecyclerView() {
        imageList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        imageList.adapter = adapter
    }

    private fun initView() {
        swipeRefresh.isEnabled = false
        ed_search.visibility = View.GONE
        categoryList.visibility = View.GONE

        adapter = ImageLocalAdapter(context)
    }

}
