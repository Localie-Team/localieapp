package com.example.localieapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.adapter.EarnGridAdapter
import com.example.localieapp.model.Coupon
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

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

    private val winIdx = 7

    private var winningCoupon: Coupon? = null

    private var columnWin = Random.nextInt(spanCount)

    private var offset = Random.nextInt(1, spanCount + 1)

    //private var offset = 0

    private lateinit var earnGridAdapter: EarnGridAdapter
    private lateinit var shuffleHandler: Handler

    private val shuffleLock = Object()


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

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step = view.findViewById(R.id.step_forward_psa_button)


        couponMatrix = ArrayList()
        dataset = ArrayList()
        earnGridAdapter = EarnGridAdapter(requireContext(), dataset)

        recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
        recyclerView!!.adapter = earnGridAdapter;
        recyclerView!!.layoutManager = GridLayoutManager(requireContext(), spanCount);

        shuffleCoupons()
        earnGridAdapter.updateDataSet(dataset)
        if(couponMatrix.size != 0){
            for (row in couponMatrix){
                Log.d("matrix1", "---------------------")
                for (coupon in row) {
                    Log.d("matrix1", coupon.coordinate.toString())
                }
            }
        }








                // Use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                recyclerView!!.setHasFixedSize(true)
                step?.setOnClickListener(View.OnClickListener {

                    if (!isActive ) {
                        isActive = true
                        shuffleCoupons()
                        content()
                    }
                })
            }

    private fun shuffleCoupons(){
        shuffleHandler = Handler()
        refreshCount = 0
        columnWin = Random.nextInt(spanCount)
        offset = Random.nextInt(1, spanCount + 1)
        couponMatrix.clear()
        dataset.clear()
        // Duplicate, shuffle and join the original list as many times are there are rows
        for (i in 0 until spanCount) {
            val shuffledList = coupons?.let { ArrayList(it) } // Create a copy of the originalList

            // Shuffle the copied list randomly
            shuffledList?.shuffle()

            if (shuffledList != null) {
                couponMatrix.add(ArrayList(shuffledList))
            }
        }

        winningCoupon = couponMatrix[0][winIdx]

        Log.d("winner", winningCoupon!!.productName.toString())


        for(i in 1 until couponMatrix.size){
            var idx = couponMatrix[i].indexOf(winningCoupon)
            if (idx != -1){
                Log.d("winner", "here $i, $idx")
                couponMatrix[i].removeAt(idx)
                couponMatrix[i].add(winIdx + offset, winningCoupon!!)
                idx = couponMatrix[i].indexOf(winningCoupon)
                Log.d("winner", "here $i, $idx")
            }
            offset++

        }

        for (row in couponMatrix){
            dataset.addAll(row.subList(0,spanCount))
        }

        for (i in 0 until dataset.size) {
            dataset[i].coordinate = i
            Log.d("dataset", dataset[i].productName.toString())
        }

    }



    // Below is the content function with horizontal movement

    fun content() {

        synchronized(shuffleLock){
            if (dataset[columnWin] != dataset[columnWin + spanCount] || dataset[columnWin] != dataset[columnWin + spanCount * 2]) {
                dataset.clear()
                for (i in 0 until couponMatrix.size){
                    val row = couponMatrix[i]

                    if (row.indexOf(winningCoupon) != columnWin){
                        val firstCoupon = row.removeAt(0)
                        row.add(firstCoupon)
                    }
                    else{
                        var temp = row.indexOf(winningCoupon)
                        Log.d("column", "$temp")
                    }

                    dataset.addAll(row.subList(0,spanCount))

                    //recyclerView!!.adapter?.notifyItemRangeChanged(i*spanCount, spanCount)
                }

                for (i in 0 until dataset.size) {
                    dataset[i].coordinate = i
                    val coord = dataset[i].coordinate
                    if(i == 0){
                        Log.d("dataset1", "NEW: $coord  " + dataset[i].productName.toString())
                    }
                    else{
                        Log.d("dataset1", "$coord  " + dataset[i].productName.toString())
                    }

                }

                refreshCount++
                screenAnimateRefresh(1500)

                shuffleHandler.post {
                    earnGridAdapter.updateDataSet(dataset)
                }

            }
            else{
                isActive = false
                val context = requireContext()
                val couponWon = winningCoupon!!.productName.toString()
                val message = "You just earned $couponWon!"
                val duration = Toast.LENGTH_LONG

                val toast = Toast.makeText(context, message, duration)

                toast.show()
            }
            //earnGridAdapter.updateDataSet(dataset)
            // If play is active, call this method at the end of content

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
        if (dataset.size != 0){
            for (i in 0 until dataset.size) {
                if(i == 0){
                    Log.d("dataset2", "NEW: $i  " + dataset[i].productName.toString())
                }
                else{
                    Log.d("dataset2", "$i  " + dataset[i].productName.toString())
                }

            }
        }
        if(couponMatrix.size != 0){
            for (row in couponMatrix){
                Log.d("matrix", "---------------------")
                for (coupon in row) {
                    Log.d("matrix", coupon.coordinate.toString())
                }
            }
        }

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
