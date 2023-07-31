package com.example.localieapp
import RecyclerViewItemDecoration
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.localieapp.R

class FirstRowStaticRecyclerView(
    context: Context
) : RecyclerView(context) {

    private var horizontalDividerResId: Int = R.drawable.divider // Replace with your horizontal divider resource
    private var verticalDividerResId: Int = R.drawable.divider_vertical     // Replace with your vertical divider resource

    private val itemDecoration = RecyclerViewItemDecoration(
        context,
        horizontalDividerResId,
        verticalDividerResId
    )

    private var firstRowRecyclerView: RecyclerView? = null

    init {
        itemDecoration.setFirstRowRecyclerView(this)
        addItemDecoration(itemDecoration)
    }

    fun setFirstRowRecyclerView(recyclerView: RecyclerView) {
        firstRowRecyclerView = recyclerView
    }

    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        if (this == firstRowRecyclerView) {
            // Prevent horizontal scrolling for the first row
            return e.actionMasked == MotionEvent.ACTION_MOVE
        }
        return super.onInterceptTouchEvent(e)
    }
}
