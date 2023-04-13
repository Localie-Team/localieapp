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
import com.example.localieapp.adapter.GridAdapter
import com.example.localieapp.data.Datasource
import com.example.localieapp.model.Coupon
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.ktx.firestore
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
    //    private var recyclerView: RecyclerView? = view?.findViewById<RecyclerView>(R.id.deals_recycler_view);
    private var coupons: List<Coupon>? = null
    private var listOfCoupons = ArrayList<Coupon>()

    private var step: Button? = null

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_earn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var listOfCoupons = ArrayList<Coupon>()
        step = view.findViewById(R.id.step_forward_psa_button)

        db.collection("coupons").get()
            .addOnSuccessListener{ documents ->
                for(document in documents){
                    listOfCoupons!!.add(Coupon(0, document.data!!.get("url").toString(), document.data!!.get("product").toString()))
                }

                for (i in listOfCoupons!!.indices) {
//            print(i);
                    listOfCoupons!![i].coordinate = i;
                }


                recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
                recyclerView!!.adapter = GridAdapter(requireContext(), listOfCoupons!!);
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
            }

    }
//    override fun onResume() {
//        super.onResume()
//        step?.setOnClickListener(View.OnClickListener {
////            public fun onClick(View view) {
////
////            }
//        })
//    }


//    fun content() {
////        val range: Int = coupons!!.size;
//        val range: Int = listOfCoupons!!.size;
//        val used = mutableListOf<Int>()
//        for (i in coupons!!.indices) {
////            print(i);
//            var current: Int = (0..range - 1).random();
//            while (used.contains(current)) {
//                current = (0..range - 1).random();
//            }
//            used.add(current)
//            coupons!![i].coordinate = current;
//        }
//        recyclerView!!.adapter?.notifyItemRangeChanged(0, range)
//
//        used.clear()
//
//        if (isActive) {
//            // If play is active, call this method at the end of content
//            screenAnimateRefresh(1500)
//        }
//    }

    // TODO: remove content method that is not in use

//    fun content() {
////        val range: Int = coupons!!.size;
//        val range: Int = listOfCoupons!!.size;
//        val used = mutableListOf<Int>()
//        for (i in listOfCoupons!!.indices) {
////            print(i);
//            var current: Int = (0..range - 1).random();
//            while (used.contains(current)) {
//                current = (0..range - 1).random();
//            }
//            used.add(current)
//            listOfCoupons!![i].coordinate = current;
//        }
//        recyclerView!!.adapter?.notifyItemRangeChanged(0, range)
//
//        used.clear()
//
//        if (isActive) {
//            // If play is active, call this method at the end of content
//            screenAnimateRefresh(1500)
//        }
//    }

    // Below is the content function with horizontal movement

    fun content() {
        val numColumns = 3;
        val numRows = 3;
        val range: Int = listOfCoupons!!.size;
        val used = mutableListOf<Int>()

        for (i in listOfCoupons!!.indices) {
            if(listOfCoupons[i].coordinate == listOfCoupons.size - 1){
                listOfCoupons!![i].coordinate = 0;
            }
            else{
                listOfCoupons!![i].coordinate += 1;
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


//        val range: Int = coupons!!.size;
//        val used = mutableListOf<Int>()
//        for (i in coupons!!.indices) {
////            print(i);
//            var current : Int = (1..range).random();
//            while (used.contains(current)) {
//                current = (1..range).random();
//            }
//            used.add(current)
//            coupons!![i].coordinate = current;
//        }
//        recyclerView!!.adapter?.notifyItemRangeChanged(0,range)
//
//
//        // put your code here...
//    }


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
                }
            }
    }
}
