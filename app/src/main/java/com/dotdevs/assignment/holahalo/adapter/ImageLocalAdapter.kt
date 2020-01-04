package com.dotdevs.assignment.holahalo.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dotdevs.assignment.holahalo.R
import com.dotdevs.assignment.holahalo.database.model.Image
import kotlinx.android.synthetic.main.image_list_item.view.*

class ImageLocalAdapter(val context: Context?): RecyclerView.Adapter<ImageLocalAdapter.ViewHolder>() {

    private var imageList = listOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = imageList[position]

        holder.bind(image)

        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(image.pageURL)
            context?.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(image: Image) {
            itemView.username.text = image.user
            itemView.imageLikeCount.text = image.likes.toString()
            itemView.imageViewCount.text = image.views.toString()
            itemView.imageCommentCount.text = image.views.toString()

            Glide.with(itemView).load(image.webFormatURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.imageContainer)

            Glide.with(itemView).load(image.userImageURL)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.userImage)
        }
    }

    fun addImages(imageList: List<Image>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }
}