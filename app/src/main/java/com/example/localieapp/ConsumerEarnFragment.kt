package com.example.localieapp

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.EarnGridAdapter
import com.example.localieapp.model.Coupon
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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

    private var step: Button? = null

    private var isActive: Boolean = false

    val db = Firebase.firestore;

    private val spanCount = 3

    lateinit var dataset: ArrayList<Coupon>

    private lateinit var couponMatrix: ArrayList<ArrayList<Coupon>>

    private var refreshCount = 0


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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_earn, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step = view.findViewById(R.id.step_forward_psa_button)

        couponMatrix = ArrayList()
        dataset = ArrayList()


        // Duplicate and join the originalList three times
        for (i in 0 until spanCount) {
            val shuffledList = coupons?.let { ArrayList(it) } // Create a copy of the originalList

            // Shuffle the copied list randomly
            shuffledList?.shuffle()

            if (shuffledList != null) {
                couponMatrix.add(ArrayList(shuffledList))
            }
        }

        for (row in couponMatrix){
            dataset.addAll(row.subList(0,spanCount))
        }

        for (i in 0 until dataset.size) {
            dataset[i].coordinate = i
        }



//        var coord = 0
//                for (i in couponMatrix.indices) {
//                    if (coupons!!.size % (i + 1) < SpanCount){
//                        couponMatrix[i].coordinate = coord
//                        dataset.add(couponMatrix[i])
//                        Log.d("index", i.toString())
//
//                        coord++
//
//                    }
//                    else{
//                        couponMatrix[i].coordinate = -1
//                    }
//
//                }
//
//        for (i in dataset.indices){
//            dataset[i].productName?.let { Log.d("Coupon", it) }
//            Log.d("Coupon", dataset[i].coordinate.toString())
//        }




                recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
                recyclerView!!.adapter = EarnGridAdapter(requireContext(), dataset);
                recyclerView!!.layoutManager = GridLayoutManager(requireContext(), spanCount);

                // Use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView!!.setHasFixedSize(true)
                step?.setOnClickListener(View.OnClickListener {

                    if (!isActive ) {
                        isActive = true
                        content()
                    }
                    else {
                        isActive = false
                    }
                })
            }



    // Below is the content function with horizontal movement

    fun content() {
        val numColumns = spanCount
        val numRows = spanCount
        val range: Int = dataset.size
        val used = mutableListOf<Int>()

        val winIdx = 7

        dataset.clear()


        for (row in couponMatrix){
            val firstCoupon = row.removeAt(0)
            row.add(firstCoupon)
            dataset.addAll(row.subList(0,spanCount))
        }

        for (i in 0 until dataset.size) {
            dataset[i].coordinate = i
        }
        recyclerView!!.adapter?.notifyItemRangeChanged(0, range)

        used.clear()

        if (isActive && refreshCount <= 5) {
            // If play is active, call this method at the end of content
            refreshCount++
            screenAnimateRefresh(1500)
        }
        else{
            isActive = false
            refreshCount = 0
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
