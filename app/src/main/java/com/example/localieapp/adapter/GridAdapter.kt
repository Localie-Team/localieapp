package com.example.localieapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.R
import com.example.localieapp.model.Coupon

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


        for (i in dataset.indices) {
//            print(i);
//            coupons!!.get(i).coordinate = i;
            if (dataset[i].coordinate == position) {
                val item = dataset[i]
                Log.d("TAG", context.resources.getString(item.stringResourceId))
                holder.textView.text = context.resources.getString(item.stringResourceId)
//        holder.textView.text = item.productName;
//                holder.imageView.setImageResource(R.drawable.image1)
                holder.imageView.setImageResource(item.imageResourceId)
//                holder.imageView.setImageResource(R.drawable.(context.resources.getDr))
            }
        }

//        val item = dataset[position]
//        holder.textView.text = context.resources.getString(item.stringResourceId)
////        holder.textView.text = item.productName;
//        holder.imageView.setImageResource(R.drawable.image1)

    }
}