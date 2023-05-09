package com.example.localieapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.localieapp.ConsumerSettingActivity
import com.example.localieapp.CouponDetailsActivity
import com.example.localieapp.R
import com.example.localieapp.model.Coupon
import com.google.android.material.card.MaterialCardView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class DealsGridAdapter(private val context: Context, private val dataset: List<Coupon>)
    : RecyclerView.Adapter<DealsGridAdapter.ItemViewHolder>() {

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
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_grid_item, parent, false)

        return ItemViewHolder(adapterLayout as MaterialCardView)
    }

    // This function returns the number of items in the list of coupons
    override fun getItemCount(): Int {
        return dataset.size
    }

    // This function binds the data to the views for each item in the RecyclerView
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        // This is required for getting the images from Firebase Storage
        val storage = Firebase.storage
        var item: Coupon? = null

        // Maps through the list of coupons to find the one that matches the current position
        couponIndexMap[position]?.let { index ->
            item = dataset[index]
            val httpsReference = item!!.url?.let {
                storage.getReferenceFromUrl(
                    it
                )
            }
            holder.textView.text = item!!.productName;
            Glide.with(context)
                .load(httpsReference)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(holder.imageView)
        }
        holder.itemView.setOnClickListener(View.OnClickListener() {
            Log.d("onBindViewHolderDeals:", position.toString())



            val mainIntent = Intent(context, CouponDetailsActivity::class.java)
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            mainIntent.putExtra("Coupon", item)
            context.startActivity(mainIntent)

        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener()
        {
            holder.view.setChecked(!holder.view.isChecked)
            true
        }
        )
    }
}





