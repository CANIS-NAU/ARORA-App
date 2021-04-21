package com.example.aorora.butterflyGame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import com.example.aorora.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Darius Dumel on 2/10/2021. any questions? get help @ dtd66@nau.edu
 */
public class Basket extends androidx.appcompat.widget.AppCompatImageButton {

    final private int IMAGE_RESOURCE = R.drawable.jar_button;

    boolean isViewExpanding = false;

    public Map<String, Integer> basketContents;

    float scaleX;
    float scaleY;

    public Basket(Context context) {
        super(context);
        innitBasket();
    }

    public Basket(Context context, AttributeSet attrs) {
        super(context, attrs);
        innitBasket();
    }

    public Basket(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        innitBasket();
    }

    private void innitBasket() {
        basketContents = new HashMap<String, Integer>();
        this.setImageResource(IMAGE_RESOURCE);
        scaleX = this.getScaleX();
        scaleY = this.getScaleY();

        Log.println(Log.ASSERT, "start_scaleX", String.valueOf(scaleX));
        Log.println(Log.ASSERT, "start_scaleY", String.valueOf(scaleY));

    }

    public void expandBasket(float scale) {

        if (!isViewExpanding) {
            isViewExpanding = true;
            this.animate()
                    .scaleXBy(scale)
                    .scaleYBy(scale)
                    .setDuration(10)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isViewExpanding = false;
                        }
                    });
        }
    }

    public void collapseBasket(float scale) {

        if (!isViewExpanding) {
            isViewExpanding = true;
            this.animate()
                    .scaleXBy(-scale)
                    .scaleYBy(-scale)
                    .setDuration(10)
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            isViewExpanding = false;
                        }
                    });
        }
    }

    public void addToBasket(String database_field, int item_count) {
        int newCount = 0;

        if (basketContents.containsKey(database_field)) {
            newCount = basketContents.get(database_field);
        }

        newCount += item_count;

        basketContents.put(database_field, newCount);
    }

}
