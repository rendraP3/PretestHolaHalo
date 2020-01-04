package com.dotdevs.assignment.holahalo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dotdevs.assignment.holahalo.R
import com.dotdevs.assignment.holahalo.api.model.Image
import com.dotdevs.assignment.holahalo.ui.DetailActivity
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImageListAdapter(private val context: Context?) : RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    private var imageList = arrayListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]

        holder.bind(image)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("data", image)
            context?.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(image: Image) {
            itemView.username.text = image.user
            itemView.imageLikeCount.text = image.likes.toString()
            itemView.imageViewCount.text = image.views.toString()
            itemView.imageCommentCount.text = image.views.toString()

            Glide.with(itemView).load(image.webformatURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.imageContainer)

            Glide.with(itemView).load(image.userImageURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.userImage)
        }

    }

    fun addItem(imageList: List<Image>) {
        this.imageList.clear()
        this.imageList.addAll(imageList)
        notifyDataSetChanged()
    }

    fun clearItem() {
        this.imageList = arrayListOf()
        notifyDataSetChanged()
    }

}