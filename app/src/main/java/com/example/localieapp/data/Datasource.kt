package com.example.localieapp.data
import com.example.localieapp.R
import com.example.localieapp.model.Coupon

class Datasource {
    fun loadCoupons(): List<Coupon> {
        return listOf<Coupon>(
            Coupon(R.string.coupon1, R.drawable.image1),
            Coupon(R.string.coupon2, R.drawable.image2),
            Coupon(R.string.coupon3, R.drawable.image3),
            Coupon(R.string.coupon4, R.drawable.image4),
            Coupon(R.string.coupon5, R.drawable.image5),
            Coupon(R.string.coupon6, R.drawable.image6),
            Coupon(R.string.coupon7, R.drawable.image7),
            Coupon(R.string.coupon8, R.drawable.image8),
            Coupon(R.string.coupon9, R.drawable.image9),
            Coupon(R.string.coupon10, R.drawable.image10)
        )
    }
}