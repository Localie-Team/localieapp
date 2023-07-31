package com.example.localieapp

import RecyclerViewItemDecoration
import android.annotation.SuppressLint
import android.content.Context
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
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.localieapp.adapter.EarnGridAdapter
import com.example.localieapp.model.Coupon
import com.example.localieapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
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
    private var param3: String? = null

    private var recyclerView: RecyclerView? = null

    private var coupons: ArrayList<Coupon>? = null
    private var scrollHandler: Handler? = null
    private var isAutoScrolling = false

    private var userRef: String? = null

    private var user_data: User? = null

    private var step: Button? = null

    private var endSession: Button? = null

    private var isActive: Boolean = false

    val db = Firebase.firestore;

    private val spanCount = 4 // This will set the number of cols displayed

    lateinit var dataset: ArrayList<Coupon>

    private lateinit var couponMatrix: ArrayList<ArrayList<Coupon>>

    private var shoppingCoupons: ArrayList<Coupon>? = null

    private var refreshCount = 0

    private val winIdx = 2 // The 44th icon (3rd section or section_2 and 12th row)

    private var counterTester = 0

    private var winningCoupon: Coupon? = null

    private var rowWin = 0 // Will hold rand row from span of visible rows

    private var offset = Random.nextInt(1, spanCount + 1)

    private lateinit var earnGridAdapter: EarnGridAdapter

    private val shuffleLock = Object()

    private val shuffleDuration = 8000L
    private lateinit var shuffleTimer: Timer
    private lateinit var shuffleHandler: Handler

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM2)
        }

        firebaseAuth = FirebaseAuth.getInstance()



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        coupons = arguments?.getParcelableArrayList<Coupon>("coupons")
        userRef = arguments?.getString("userRef")
        user_data = arguments?.getParcelable("user")
        ShoppingBag.array_of_coupons.clear()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_earn, container, false)
    }

    //TODO: Look into the way this algorithm. It looks inefficient
    private fun presetCoupons(shoppingCoupons: ArrayList<Coupon>) {
        var cnt = ShoppingBag.list_of_coupons.size
        if (cnt > 8) {
            cnt = 8
        }
        for(i in 1 until cnt){
            var rndm = Random.nextInt(0, cnt + 1 - i)
            shoppingCoupons.removeAt(rndm)
        }
        var j = 0
        for (couponId in ShoppingBag.list_of_coupons) {
            if (j == 8) {
                break
            }
            for (temp in coupons!!) {
                if (couponId == temp.UID) {
                    shoppingCoupons.add(temp.clone())
                }
            }
            j++
        }

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        step = view.findViewById(R.id.step_forward_psa_button)
        endSession = view.findViewById(R.id.end_session_button)

        couponMatrix = ArrayList()
        dataset = ArrayList()
        earnGridAdapter = EarnGridAdapter(requireContext(), dataset)

        recyclerView = view.findViewById<RecyclerView>(R.id.deals_recycler_view);
        (recyclerView?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView!!.adapter = earnGridAdapter;
        recyclerView!!.layoutManager = GridLayoutManager(requireContext(), spanCount);
//        recyclerView!!.layoutManager = GridLayoutManager(requireContext(), spanCount, RecyclerView.HORIZONTAL, false)
//        recyclerView!!.layoutManager = CustomGridLayoutManager(requireContext(), spanCount, RecyclerView.HORIZONTAL, false)
//        recyclerView!!.layoutManager = HorizontalGridLayoutManager(requireContext(),spanCount);
        recyclerView!!.setHasFixedSize(true)

        recyclerView!!.addItemDecoration(RecyclerViewItemDecoration(requireContext(), R.drawable.divider, R.drawable.divider_vertical))
        shoppingCoupons?.clear()

        shoppingCoupons = coupons?.clone() as ArrayList<Coupon>
        shuffleCouponsOnStart()
        //##EXPERIMENTAL
//        dataset.removeAt(dataset.size - 1)
        earnGridAdapter.updateDataSet(dataset)

                // Use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                            recyclerView!!.setHasFixedSize(true)
                step?.setOnClickListener(View.OnClickListener {

                    if (!isActive ) {
                        isActive = true
                        shoppingCoupons?.clear()
//                        shoppingCoupons = coupons?.clone() as ArrayList<Coupon>
                        for (c in coupons!!){
                            shoppingCoupons!!.add(c.clone())
                        }

                        presetCoupons(shoppingCoupons!!)
//                        shuffleCouponsOnStart()
//                        earnGridAdapter.updateDataSet(dataset)
                        Thread.sleep(15)
                        content()
                    }
                    else {
                        isActive = false
                        stopAutoScroll()
                    }
                })
                endSession?.setOnClickListener(View.OnClickListener {
//                    val shopping_bag_list: List<String>? = user_data?.cart
//                    val shopping_bag_list: List<String>? = user_data?.cart
//                    if (shopping_bag_list != null) {
//                        for (i in shopping_bag_list.indices) {
//                            val userRef = db.collection("users").document(userRef.toString())
//                    /*  TODO: CHECK Which Is More Efficient? Passing in userRef (string) or
//                            looking in collection for equal to user?.ID
//                    */
//                            userRef
//                                .update("cart",  FieldValue.arrayRemove(shopping_bag_list[i]))
//                                .addOnSuccessListener { Log.d("removed from cart", "DocumentSnapshot successfully updated!") }
//                                .addOnFailureListener { e -> Log.w("cant remove from cart", "Error updating document", e) }
//                        }
//                        ShoppingBag.list_of_coupons.clear()
//                        ShoppingBag.array_of_coupons.clear()
//                    }
                    if (isActive == false) {
                        var fragment: Fragment? = null
                        fragment = ConsumerEarnFragment1();
                        fragment.arguments = arguments
                        /*
                    **THIS Is the setup for switching to earn fragment when the "add to bag" button is clicked
                    * The problem is that it does not bundle the coupon and user arguments like done in
                    * ConsumerDashboardActivity
                    *
                    */
                        val transaction = activity?.supportFragmentManager?.beginTransaction()
                        transaction?.replace(R.id.content, fragment)
                        transaction?.disallowAddToBackStack()
                        transaction?.commit()
                    }

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

    private fun shuffleCouponsOnStart(){
        shuffleHandler = Handler()
        refreshCount = 0
        rowWin = Random.nextInt(6)
        offset = Random.nextInt(1, spanCount + 1)
        couponMatrix.clear()
        dataset.clear()
        val shuffledList = ArrayList<Coupon>()
        // Duplicate, shuffle and join the original list as many times are there are rows
        //for (i in 0 until spanCount)
        for (i in 0 until 6) {
            for (c in shoppingCoupons!!){
                shuffledList!!.add(c.clone())
            }
            // Shuffle the copied list randomly
            shuffledList!!.shuffle()

            couponMatrix.add(ArrayList(shuffledList))

            shuffledList.clear()
        }

        winningCoupon = couponMatrix[2][winIdx]


        for(i in 0 until spanCount){
            if (i == 2) continue;
            val idx = findIndexOfProduct(couponMatrix[i],
                winningCoupon!!.productName.toString(), winningCoupon!!.coupon_value.toString()
            )
            if (idx != -1){
                var thisWin = couponMatrix[i].removeAt(idx)
                var tempHolder = couponMatrix[2].removeAt(i)
                couponMatrix[i].add(idx, tempHolder)
                couponMatrix[2].add(i,thisWin)
            }
//            offset++

        }



        var idx = 0
        for (row in couponMatrix){
            for (i in 0 until row.size){
                row[i].coordinate = idx
                idx++
            }
            dataset.addAll(row.subList(0,row.size))
//            dataset.addAll(row.subList(0,spanCount))
        }


    }

//    private fun findIndexOfProduct(products: ArrayList<Coupon>, targetProductName: String, targetValue: String): Int {
//        return products.indexOfFirst { it.productName == targetProductName && it.coupon_value == targetValue }
//    }
    private fun findIndexOfProduct(products: ArrayList<Coupon>, targetProductName: String, targetValue: String): Int {
        return products.indexOfFirst { it.productName == targetProductName && it.coupon_value == targetValue }
    }

    private fun shuffleCoupons(){
        dataset.clear()
        for (i in 0 until couponMatrix.size){
            val row = couponMatrix[i]
            val idx = findIndexOfProduct(couponMatrix[i],
                winningCoupon!!.productName.toString(), winningCoupon!!.coupon_value.toString()
            )

//            if (idx != columnWin){
//                val firstCoupon = row.removeAt(0)
//                row.add(firstCoupon)
//            }

            dataset.addAll(row.subList(0,row.size-1))
        }

//        for (i in 0 until dataset.size) {
//            dataset[i].coordinate = i
//        }
    }



    // Below is the content function with horizontal movement

    fun content() {

//        synchronized(shuffleLock){
//            if (isActive && refreshCount < winIdx + offset) {
                shuffleCoupons()
//                shuffleHandler.post {
//                    earnGridAdapter.updateDataSet(dataset)
//                }
                refreshCount++
//                screenAnimateRefresh(250)
                startAutoScroll()

//            }
//            else{
//                isActive = false
//                stopAutoScroll()
//
//                updateUserWins(winningCoupon!!)
//
//            }

//        }

    }

    private fun updateUserWins(coupon: Coupon){



        val user = firebaseAuth!!.currentUser
        val userRef = db.collection("users").whereEqualTo("UID", user?.uid).get()
        userRef.addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                val winList = document.get("win") as? ArrayList<String>
                if (winList != null) {
                    val context = requireContext()
                    val couponWon = coupon.productName.toString()
                    val message = "You just earned $couponWon!"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(context, message, duration)

                    toast.show()

                    winList.add(coupon.UID.toString())
                    val data = hashMapOf("win" to winList)
                    document.reference.update(data as Map<String, Any>)
                        .addOnSuccessListener {
                            Log.d("added", "here")
                        }
                        .addOnFailureListener { exception ->
                            }
                }
            }
        }.addOnFailureListener { exception ->
            // Handle the failure case
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAutoScroll() // Stop automatic scrolling when the fragment is destroyed
    }

    private fun startAutoScroll() {
        isAutoScrolling = true
//        totalScrollDistance = 0
        val scrollSpeed = 200 // You can adjust the scroll speed here (higher values make it faster)
        scrollHandler = Handler()
        scrollHandler?.post(object : Runnable {
            override fun run() {
                if (isAutoScrolling) {
                    val maxScroll = recyclerView?.computeHorizontalScrollRange() ?: 0
//                    val scrollPosition = (totalScrollDistance + scrollSpeed) % maxScroll
                    recyclerView?.smoothScrollBy(0, scrollSpeed)
                    counterTester += 1
                    Log.d("CounterTester",counterTester.toString())
                    if (counterTester == 11) stopAutoScroll()
                    scrollHandler?.postDelayed(this, 500) // Adjust the delay here (50 ms = 50 milliseconds)
                }
            }
        })
    }

    private fun stopAutoScroll() {
        isAutoScrolling = false
        scrollHandler?.removeCallbacksAndMessages(null)
        if (counterTester == 11) {
            updateUserWins(winningCoupon!!)
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
