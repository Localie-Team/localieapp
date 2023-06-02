package com.example.localieapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.EarnGridAdapter
import com.example.localieapp.adapter.GridAdapter
import com.example.localieapp.data.Datasource
import com.example.localieapp.model.Coupon
import com.example.localieapp.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.util.Collections

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EarnFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsumerEarnFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var recyclerView: RecyclerView? = null

    private var coupons: ArrayList<Coupon>? = null

    private var user_data: User? = null

    private var step: Button? = null

    private var endSession: Button? = null

    private var isActive: Boolean = false

    val db = Firebase.firestore;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coupons = arguments?.getParcelableArrayList<Coupon>("coupons")
        user_data = arguments?.getParcelable("user")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_earn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step = view.findViewById(R.id.step_forward_psa_button)
        endSession = view.findViewById(R.id.end_session_button)

                for (i in coupons!!.indices) {
                    coupons!![i].coordinate = i;
                }


                recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
                recyclerView!!.adapter = EarnGridAdapter(requireContext(), coupons!!);
                recyclerView!!.layoutManager = GridLayoutManager(requireContext(), 3);

                // Use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView!!.setHasFixedSize(true)
                step?.setOnClickListener(View.OnClickListener {

                    if (!isActive) {
                        isActive = true
                        content()
                    }
                    else {
                        isActive = false
                    }
                })
                endSession?.setOnClickListener(View.OnClickListener {
                    val shopping_bag_list: List<String>? = user_data?.cart
                    if (shopping_bag_list != null) {
                        for (i in shopping_bag_list.indices) {
                            //TODO: Change it to refer to current user (once its in database)
                            val userRef = db.collection("users").document("rJVvDNzYeFExHs04YTGi")
                            userRef //TODO: have it when the user clicks "add to shopping bag" it updates shopping_bag_list right away
                                .update("cart",  FieldValue.arrayRemove(shopping_bag_list[i]))
                                .addOnSuccessListener { Log.d("removed from cart", "DocumentSnapshot successfully updated!") }
                                .addOnFailureListener { e -> Log.w("cant remove from cart", "Error updating document", e) }
                        }
                    }

//                    db.collection("users").whereEqualTo("UID", "rJVvDNzYeFExHs04YTGi").get()
//                        .addOnSuccessListener{ Udocuments ->
//                            for(Udocument in Udocuments) {
//                                Udocument.get("cart")
//                            }
//                }
////                    val userRef = db.collection("users").document("rJVvDNzYeFExHs04YTGi")
////                    userRef
////                        .ge


            })

//        endSession?.setOnClickListener(View.OnClickListener {
//            val userRef = db.collection("users").document("rJVvDNzYeFExHs04YTGi").g
//
//            val array = userRef
////                    val userRef = db.collection("users").document("rJVvDNzYeFExHs04YTGi")
////                    userRef
////                        .ge
//
//        })
    }



    // Below is the content function with horizontal movement

    fun content() {
        val numColumns = 3;
        val numRows = 3;
        val range: Int = coupons!!.size;
        val used = mutableListOf<Int>()

        for (i in coupons!!.indices) {
            if(coupons!![i].coordinate == coupons!!.size - 1){
                coupons!![i].coordinate = 0;
            }
            else{
                coupons!![i].coordinate += 1;
            }

        }
        recyclerView!!.adapter?.notifyItemRangeChanged(0, range)

        used.clear()

        if (isActive) {
            // If play is active, call this method at the end of content
            screenAnimateRefresh(1500)
        }
    }

    fun screenAnimateRefresh(milliseconds : Long) {
        // Looper.prepare()
        val handler = Handler()
        val runnable = Runnable() {
            run();
        };

        handler.postDelayed(runnable, milliseconds)
    }

    @Override
    fun run() {
        content();
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EarnFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConsumerEarnFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putParcelableArrayList("coupons", coupons)
                }
            }
    }
}
