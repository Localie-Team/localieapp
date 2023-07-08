package com.example.localieapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.DealsGridAdapter
//import com.example.localieapp.adapter.ExpandableListGridAdapter
import com.example.localieapp.model.Coupon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsumerDealsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    private var recyclerView: RecyclerView? = null
    private var coupons: ArrayList<Coupon>? = null
    private var userRef: String? = null
//    public lateinit var checkedSet: MutableList<String>
    private var shoppingBagButton: Button? = null
    val db = Firebase.firestore;
    private var firebaseAuth: FirebaseAuth? = null

//    private var expListView: ExpandableListView? = null
//    private var adapter: ExpandableListGridAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM2)
//            coupons = it.getParcelableArrayList<Coupon>("coupons")
        }
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coupons = arguments?.getParcelableArrayList<Coupon>("coupons")
        userRef = arguments?.getString("userRef")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_deals, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingBagButton = view.findViewById(R.id.add_to_shopping_bag)
        // Initialize the checkedSet
//        checkedSet = mutableListOf()

//        var listOfCoupons = ArrayList<Coupon>()



//        db.collection("coupons").get()
//            .addOnSuccessListener{ documents ->
//                for(document in documents){
//                    listOfCoupons.add(Coupon(0, document.data!!.get("url").toString(), document.data!!.get("product").toString()))
//                }
//
//                for (i in listOfCoupons!!.indices) {
//                    listOfCoupons!![i].coordinate = i;
//                }


        for (i in coupons!!.indices) {
//            print(i);
//            listOfCoupons!![i].coordinate = i;
            coupons!!.get(i).coordinate = i;
        }
            recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
//            recyclerView!!.adapter = DealsGridAdapter(requireContext(), coupons!!);
            recyclerView!!.adapter = DealsGridAdapter(requireContext(), coupons!!);
            recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 3);

            // Use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView!!.setHasFixedSize(true)
//        }

        shoppingBagButton?.setOnClickListener(View.OnClickListener {
            if (ShoppingBag.array_of_coupons.size > 0) {
                var flag = false
                for (j in 0 until ShoppingBag.array_of_coupons.size) {
                    val key = ShoppingBag.array_of_coupons[j]
//                    Log.d("checkedCouponId", ShoppingBag.array_of_coupons[j])
                    val userRef = db.collection("users").document(userRef.toString())
//                    val userRef = db.collection("users").whereEqualTo("UID", firebaseAuth!!.currentUser!!.uid).get()
                    ShoppingBag.list_of_coupons.add(key)
                    userRef
                        .update("cart",  FieldValue.arrayUnion(key))
                        .addOnSuccessListener {
//                            ShoppingBag.list_of_coupons.add(key)
                            flag = true
                            Log.d("pushed to cart", "DocumentSnapshot successfully updated!")
                            Log.d("added to coupon list", ShoppingBag.list_of_coupons.get(ShoppingBag.list_of_coupons.size-1))}
                        .addOnFailureListener { e -> Log.w("couldnt push to cart", "Error updating document", e) }
                }

            }

        })
    }

    override fun onResume() {
//        view.findView
        super.onResume()
        Log.d("onResume()", "Im here!")
        if (ShoppingBag.array_of_coupons.size > 0) {
            for (j in 0 until ShoppingBag.array_of_coupons.size) {
                Log.d("checkedCouponId", ShoppingBag.array_of_coupons[j])
            }
        }
        if (ShoppingBag.list_of_coupons.size > 0) {
            for (j in 0 until ShoppingBag.list_of_coupons.size) {
                Log.d("shoppingbag_coupon_list", ShoppingBag.list_of_coupons[j])
            }

        }

//        var cnt = recyclerView?.childCount
//        for (i in 0..cnt!!)
//        {
//            var child: MaterialCardView = recyclerView?.getChildAt(i) as MaterialCardView;
//        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DealsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsumerDealsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putParcelableArrayList("coupons", coupons)
                }
            }
    }
}