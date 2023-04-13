package com.example.localieapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.localieapp.R
import com.example.localieapp.model.Coupon
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileGridAdapter(private val context: Context, private val dataset: List<Coupon>)
    : RecyclerView.Adapter<ProfileGridAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title);
        val imageView: ImageView = view.findViewById(R.id.item_image);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_grid_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // This is required for getting the images
        val storage = Firebase.storage



        for (i in dataset.indices) {
//            print(i);
//            coupons!!.get(i).coordinate = i;
            if (dataset[i].coordinate == position) {
                val item = dataset[i]
                //this creates a url refrence
                val httpsReference = item.url?.let {
                    storage.getReferenceFromUrl(
                        it
                    )
                }
                holder.textView.text = item.productName;
                //this loads the imageview with the image using glide
                GlideApp.with(context)
                  .load(httpsReference)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                  .into(holder.imageView)
            }
        }

    }
}