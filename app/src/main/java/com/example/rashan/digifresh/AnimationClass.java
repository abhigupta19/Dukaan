package com.example.rashan.digifresh;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Mahendroo on 22-07-2016.
 */
public class AnimationClass {

    public static void recyclerAnimationSabzi(RecyclerView.ViewHolder holder, boolean goesDown) {

        AnimatorSet animatorset = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 200 : -200, 0);
        animatorTranslateY.setDuration(1000);

        animatorset.playTogether(animatorTranslateY);
        animatorset.start();
    }

    public static void recyclerAnimationVendor(RecyclerView.ViewHolder holder, boolean goesDown) {

        AnimatorSet animatorset = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationX", goesDown == true ? 200 : -200, 0);
        animatorTranslateY.setDuration(1000);

        animatorset.playTogether(animatorTranslateY);
        animatorset.start();
    }


}
