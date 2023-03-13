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
import com.example.localieapp.R
import com.example.localieapp.model.Coupon
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class GridAdapter(private val context: Context, private val dataset: List<Coupon>)
    : RecyclerView.Adapter<GridAdapter.ItemViewHolder>() {

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
        val storage = Firebase.storage



        for (i in dataset.indices) {
//            print(i);
//            coupons!!.get(i).coordinate = i;
            if (dataset[i].coordinate == position) {
                val item = dataset[i]

                val httpsReference = storage.getReferenceFromUrl(
                  item.url)
                holder.textView.text = item.productName;
                GlideApp.with(context)
                  .load(httpsReference)
                  .into(holder.imageView)
            }
        }

    }
}