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
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class EarnGridAdapter(private val context: Context, private val dataset: List<Coupon>)
    : RecyclerView.Adapter<EarnGridAdapter.ItemViewHolder>() {

    private val couponIndexMap: Map<Int, Int> = dataset.mapIndexed { index, coupon ->
        coupon.coordinate to index
    }.toMap()

    // Class holds references to the views in each item of the RecyclerView
    class ItemViewHolder(val view: MaterialCardView) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title);
        val imageView: ImageView = view.findViewById(R.id.item_image);
    }

    // This function inflates the layout for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_grid_item, parent, false)

        return ItemViewHolder(adapterLayout as MaterialCardView)
    }

    // This function returns the number of items in the list of coupons
    override fun getItemCount(): Int {
        return dataset.size
    }

    // This function binds the data to the views for each item in the RecyclerVie
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // This is required for getting the images from Firebase Storage
        val storage = Firebase.storage



        for (i in dataset.indices) {
            if (dataset[i].coordinate == position) {
                val item = dataset[i]
                // This creates a URL reference for the coupon's image in Firebase Storage
                val httpsReference = item.url?.let {
                    storage.getReferenceFromUrl(
                        it
                    )
                }
                // Set the text of the TextView to the coupon's product name
                holder.textView.text = item.productName;
                // Use the Glide library to load the coupon's image into the ImageView
                GlideApp.with(context)
                    .load(httpsReference)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.imageView)
            }
        }
        holder.itemView.setOnClickListener(View.OnClickListener() {
            Log.d("onBindViewHolder:", position.toString())
        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener()
        {
            holder.view.setChecked(!holder.view.isChecked)
            true
        }
        )
    }
}
