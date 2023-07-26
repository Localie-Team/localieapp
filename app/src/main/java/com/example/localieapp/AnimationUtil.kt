package com.example.localieapp

import android.animation.ObjectAnimator
import androidx.recyclerview.widget.RecyclerView

object AnimationUtil {
    fun animate(holder: RecyclerView.ViewHolder, goesDown: Boolean) {
//        val animatorSet = AnimatorSet()
//        val animatorTranslateY: ObjectAnimator = ObjectAnimator.ofFloat(
//            holder.itemView,
//            "translationY",
//            if (goesDown == true) 200 else -200,
//            0
//        )
//        animatorTranslateY.duration = 1000
//        val animatorTranslateX: ObjectAnimator = ObjectAnimator.ofFloat(
//            holder.itemView,
//            "translationX",
//            -50,
//            50,
//            -30,
//            30,
//            -20,
//            20,
//            -5,
//            5,
//            0
//        )
//        animatorTranslateX.duration = 1000
//        animatorSet.playTogether(animatorTranslateX, animatorTranslateY)
//
//        //animatorSet.playTogether(animatorTranslateY);
//        animatorSet.start()
        val animation = ObjectAnimator.ofFloat(holder.itemView, "translationX", 1300f)
        animation.duration = 1000
        animation.start()
    }
}