package com.example.localieapp.data
import com.example.localieapp.R
import com.example.localieapp.adapter.ExpandableListAdapter
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

    fun createListData(): Pair<ArrayList<String>, HashMap<String, List<String>>> {
        var listDataParent = ArrayList<String>()
        var listDataChild = HashMap<String, List<String>>()

        // Adding child data
        listDataParent.add("Products")
        listDataParent.add("Services")
        listDataParent.add("Attractions")

        // Adding child data
        val Products: MutableList<String> = ArrayList()
        Products.add("Product Coupon 1")
        Products.add("Product Coupon 2")
        Products.add("Product Coupon 3")
        Products.add("Product Coupon 4")
        Products.add("Product Coupon 5")
        Products.add("Product Coupon 6")
        Products.add("Product Coupon 7")
        val Services: MutableList<String> = ArrayList()
        Services.add("Service Coupon 1")
        Services.add("Service Coupon 2")
        Services.add("Service Coupon 3")
        Services.add("Service Coupon 4")
        Services.add("Service Coupon 5")
        Services.add("Service Coupon 6")
        val Attractions: MutableList<String> = ArrayList()
        Attractions.add("Attraction Coupon 1")
        Attractions.add("Attraction Coupon 2")
        Attractions.add("Attraction Coupon 3")
        Attractions.add("Attraction Coupon 4")
        Attractions.add("Attraction Coupon 5")
        listDataChild.put(listDataParent.get(0), Products) // Header, Child data
        listDataChild.put(listDataParent.get(1), Services)
        listDataChild.put(listDataParent.get(2), Attractions)

        return Pair(listDataParent, listDataChild)
//        var listAdapter = ExpandableListAdapter(context, listDataParent, listDataChild)
//        expListView.setAdapter(listAdapter)
    }
}