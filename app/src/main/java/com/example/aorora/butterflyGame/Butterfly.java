package com.example.aorora.butterflyGame;

import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;

import android.util.AttributeSet;
import android.view.View;

import com.example.aorora.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Butterfly is a extension of a ImageView with predefined types each with unique
 * names, imageResource and id. Butterflies also have built in animations
 */
@SuppressLint("ViewConstructor")
public class Butterfly extends androidx.appcompat.widget.AppCompatImageView {

    //TODO change the string name across this and back end for better readability
    public enum Type {
        RED(
                "user_b0_count",
                new int[]{
                        R.drawable.red_1,
                        R.drawable.red_2,
                        R.drawable.red_3},
                0),
        YELLOW(
                "user_b1_count",
                new int[]{
                        R.drawable.yellow_1,
                        R.drawable.yellow_2,
                        R.drawable.yellow_3
                },
                1),
        VIOLET(
                "user_b2_count",
                new int[]{
                        R.drawable.violet_1,
                        R.drawable.violet_2,
                        R.drawable.violet_3
                },
                2),
        GREEN(
                "user_b3_count",
                new int[]{
                        R.drawable.violet_1,
                        R.drawable.violet_2,
                        R.drawable.violet_3,

                },
                3),
        BLUE("user_b4_count",
                new int[]{
                        R.drawable.blue_1,
                        R.drawable.blue_2,
                        R.drawable.blue_3,
                },
                4);

        final String dbField; //database string for backend fields | needed to update server
        final int[] imageResources;
        private int imageResource;
        final int id;

        final static Map<Integer, Butterfly.Type> map = new HashMap<>();

        private Type(String dbField, int[] imageResources, int id)
        {
            this.dbField = dbField;
            this.id = id;
            this.imageResources = imageResources;
        }

        static {
            for (Type type : Butterfly.Type.values())
            {
                map.put(type.id, type);
            }
        }

        public static Type valueOf(int typeID)
        {
            return (Type) map.get(typeID);
        }

        public static int getCount()
        {
            return Type.values().length;
        }

        public int getImageResource(int index)
        {
            return imageResources[index];
        }

        public int getId(){
            return id;
        }

        public String getDbField(){
            return dbField;
        }

        @NonNull
        @Override
        public String toString() {
            return "Type id:" + id + "database field: "
                            + dbField + " Image Resource: " + imageResource;
        }

    }

    //flag to allow recursive animations to run.
    private boolean animateFlag = true;

    private Random seed;

    public String typeString;
    public int typeId;
    public String dbField;


    public Butterfly(Context context, Type type){
        super(context);
        initButterfly(type);
    }

    public Butterfly(Context context, AttributeSet attrs, Type type) {
        super(context, attrs);
        initButterfly(type);
    }

    public Butterfly(Context context, AttributeSet attrs, int defStyle, Type type) {
        super(context, attrs, defStyle);
        initButterfly(type);
    }

    private void initButterfly(Type type) {
        seed = new Random();
        typeId = type.getId();
        dbField = type.getDbField();
        typeString = type.toString();

        int randomIndex = seed.nextInt(type.imageResources.length);
        this.setImageResource(type.getImageResource(randomIndex));
    }

    //TODO: implement bounds better
    /**
     * animates a butterfly to translate to a random point within the Bounds
     * if animateFlag is set to true.
     * Is recursive.
     * @param xBound non-inclusive max x value of translation
     * @param yBound non-inclusive max y value of translation
     */
    public void animateToRandomPoints(final int xBound, final int yBound)
    {
        if(animateFlag)
        {
            int randX = seed.nextInt(xBound);
            int randY = seed.nextInt(yBound);

            //calculate distance for determining animation duration (v=d/t)
            double distance = Math.hypot(randX - this.getX(), randY - this.getY());

            this.animate()
                    .translationX(randX - xBound/4)
                    .translationY(randY - yBound/4)
                    .setDuration(Math.round(distance / 0.3) ) // t=d/v
                    .setListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(android.animation.Animator animation) {
                            animateToRandomPoints(xBound, yBound);
                            super.onAnimationEnd(animation);
                        }
                    });
        }
    }

    /**
     * animation that shrinks view and makes invisible to then in an animation
     * have the view pop back up.
     */
    public void spawnAnimation()
    {
        this.setAlpha(0f);
        this.setScaleX(0.01f);
        this.setScaleY(0.01f);

        this.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(450)
                .start();
    }

    /**
     * animates the view to shrink and then set the visibility to GONE
     */
    public void despawnAnimation(){
        animateFlag = false;
        this.animate()
                .scaleX(0.02f)
                .scaleY(0.02f)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                });
    }


}
