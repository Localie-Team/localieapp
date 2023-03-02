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
        listDataParent.add("Fruits")
        listDataParent.add("Vegetables")
        listDataParent.add("Meats")
        listDataParent.add("Drinks")

        // Adding child data
        val Fruits: MutableList<String> = ArrayList()
        Fruits.add("Apples")
        Fruits.add("Bananas")
        Fruits.add("Mangoes")
        Fruits.add("Pineapples")
        Fruits.add("Watermelon")
        Fruits.add("Dragon Fruits")
        Fruits.add("Kiwi")
        val Vegetables: MutableList<String> = ArrayList()
        Vegetables.add("Cabbage")
        Vegetables.add("Brussels Sprouts")
        Vegetables.add("Carrots")
        Vegetables.add("Collard Greens")
        Vegetables.add("Eggplant")
        Vegetables.add("Cauliflower")
        val Meats: MutableList<String> = ArrayList()
        Meats.add("Beef")
        Meats.add("Lamb")
        Meats.add("Veal")
        Meats.add("Pork")
        Meats.add("Kangaroo")
        val Drinks: MutableList<String> = ArrayList()
        Drinks.add("Coca Cola")
        Drinks.add("Fanta")
        Drinks.add("Sprite")
        Drinks.add("Soda")
        Drinks.add("Lemonade")
        listDataChild.put(listDataParent.get(0), Fruits) // Header, Child data
        listDataChild.put(listDataParent.get(1), Vegetables)
        listDataChild.put(listDataParent.get(2), Meats)
        listDataChild.put(listDataParent.get(3), Drinks)

        return Pair(listDataParent, listDataChild)
//        var listAdapter = ExpandableListAdapter(context, listDataParent, listDataChild)
//        expListView.setAdapter(listAdapter)
    }
}