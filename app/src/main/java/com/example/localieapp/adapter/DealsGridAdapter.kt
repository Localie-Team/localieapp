package com.example.localieapp.adapter


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.localieapp.CouponDetailsActivity
import com.example.localieapp.ConsumerDealsFragment
import com.example.localieapp.R
import com.example.localieapp.ShoppingBag
import com.example.localieapp.model.Coupon
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class DealsGridAdapter(private val context: Context, private val dataset: List<Coupon>)
    : RecyclerView.Adapter<DealsGridAdapter.ItemViewHolder>() {
//    val test: MutableList<String> = ConsumerDealsFragment.
    private var db = Firebase.firestore;
    private var firebaseAuth: FirebaseAuth? = null
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
            val imageView = holder.imageView
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val imageInByte = baos.toByteArray()
            mainIntent.putExtra("Coupon2", imageInByte)
            context.startActivity(mainIntent)

        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener()
        {
            holder.view.setChecked(!holder.view.isChecked)
            val collectionRef = db.collection("coupons")
// Apply filters
//            val query = collectionRef
//                .whereEqualTo("product", item?.productName) // Filter by equality
//                .whereEqualTo("date_issued", item?.date_issued) // Filter by equality
            //TODO: The coupon's 'vendor' property holds the value of date_issued in the Coupon object (Misplaced)
            val query = collectionRef.whereEqualTo("date_issued", item?.date_issued).
            whereEqualTo("product", item?.productName)

// Perform the query
            query.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        // Process each document here
                        val key = document.id


                        Log.d("docRefId", key)
                        firebaseAuth = FirebaseAuth.getInstance()
                        var user = firebaseAuth!!.currentUser
                        if (user != null) {
                            Log.d("userUID", user.zzb().uid)
                            //TODO: Consider making it not automatically add to cart but wait for push button
                            //TODO: Make users registered actually appear in firestore collections so we update new users
//                            val washingtonRef = db.collection("users").document(user.zzb().uid)
                            // TODO: TEMP! UNTIL ABOVE TODO IS MET
//                            val washingtonRef = db.collection("users").document("rJVvDNzYeFExHs04YTGi")
//                            washingtonRef
//                                .update("cart",  FieldValue.arrayUnion(key))
//                                .addOnSuccessListener { Log.d("pushed to cart", "DocumentSnapshot successfully updated!") }
//                                .addOnFailureListener { e -> Log.w("couldnt push to cart", "Error updating document", e) }
//                            dealsFrag.checkedSet.add(key)
                            ShoppingBag.array_of_coupons.add(key)

                        }
                    }
                }

            true
        }
        )
    }
}






