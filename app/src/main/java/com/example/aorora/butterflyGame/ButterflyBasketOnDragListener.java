package com.example.aorora.butterflyGame;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Darius Dumel on 1/11/2021. any questions? get help @ dtd66@nau.edu
 */

public class ButterflyBasketOnDragListener implements View.OnTouchListener{

    public interface OnDragActionListener {
        void onDragStart(View view);
        void onDragEnd(View view);
    }

    final float SCALE = 0.3f;

    private Butterfly butterfly;
    private Basket basket;
    private View mParent;
    private boolean isDragging;
    private boolean isInitialized = false;
    private boolean viewOnTarget = false;

    private int width;
    private float xWhenAttached;
    private float maxLeft;
    private float maxRight;
    private float dX;

    private int height;
    private float yWhenAttached;
    private float maxTop;
    private float maxBottom;
    private float dY;

    private int targetWidth;
    private int targetHeight;
    private boolean isTargetExpanded = false;
    private float tX;
    private float tY;

    private OnDragActionListener mOnDragActionListener;

    public ButterflyBasketOnDragListener(Butterfly butterfly, Basket basket) {
        this(butterfly, basket, (View) butterfly.getParent(), null);
    }

    public ButterflyBasketOnDragListener(Butterfly butterfly, Basket targetView, View parent) {
        this(butterfly, targetView, parent, null);
    }

    public ButterflyBasketOnDragListener(Butterfly butterfly, Basket basket, OnDragActionListener onDragActionListener) {
        this(butterfly, basket, (View) butterfly.getParent(), onDragActionListener);
    }

    public ButterflyBasketOnDragListener(Butterfly butterfly, Basket basket, View parent, OnDragActionListener onDragActionListener) {
        initListener(butterfly, basket, parent);
        setOnDragActionListener(mOnDragActionListener);
    }

    private void initListener(Butterfly butterfly, Basket targetView, View parent) {
        this.butterfly = butterfly;
        this.basket = targetView;
        mParent = parent;
        isDragging = false;
        isInitialized = false;
    }

    private void setOnDragActionListener(OnDragActionListener OnDragActionListener) {
        mOnDragActionListener = OnDragActionListener;
    }

    public void updateBounds() {
        updateViewBounds();
        updateTargetBounds();
        updateParentBounds();
        isInitialized = true;
    }

    public void updateViewBounds() {
        width = butterfly.getWidth();
        xWhenAttached = butterfly.getX();
        dX = 0;

        height = butterfly.getHeight();
        yWhenAttached = butterfly.getY();
        dY = 0;
    }

    public void updateTargetBounds(){
        targetWidth = basket.getWidth();
        tX = basket.getX();

        targetHeight = basket.getHeight();
        tY = basket.getY();
    }

    public void updateParentBounds() {
        maxLeft = 0;
        maxRight = maxLeft + mParent.getWidth();

        maxTop = 0;
        maxBottom = maxTop + mParent.getHeight();
    }

    private boolean isViewOnTarget() {

        double dragViewCenterX = butterfly.getX() + (width/2.0);
        double dragViewCenterY = butterfly.getY() + (width/2.0);

        if(
                tX < dragViewCenterX
                        && dragViewCenterX < (tX + targetWidth)
                        && tY < dragViewCenterY
                        && dragViewCenterY < (tY + targetHeight)
        ) {
            viewOnTarget = true;
            return true;
        };

        viewOnTarget = false;
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(isDragging) {

            float[] bounds = new float[4];

            //LEFT
            bounds[0] = motionEvent.getRawX() + dX;
            if (bounds[0] < maxLeft) {
                bounds[0] = maxLeft;
            }
            //RIGHT
            bounds[2] = bounds[0] + width;
            if (bounds[2] > maxRight) {
                bounds[2] = maxRight;
                bounds[0] = bounds[2] - width;
            }
            //TOP
            bounds[1] = motionEvent.getRawY() + dY;
            if (bounds[1] < maxTop) {
                bounds[1] = maxTop;
            }
            //BOTTOM
            bounds[3] = bounds[1] + height;
            if (bounds[3] > maxBottom) {
                bounds[3] = maxBottom;
                bounds[1] = bounds[3] - height;
            }

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    onDragFinish();
                    break;
                case MotionEvent.ACTION_MOVE:
                    butterfly.animate().x(bounds[0]).y(bounds[1]).setDuration(0).start();

                    if(viewOnTarget)
                    {
                        if(!isViewOnTarget()) {
                            basket.collapseBasket(SCALE);
                        }
                    }else
                    {
                        if(isViewOnTarget()){
                            basket.expandBasket(SCALE);
                        }
                    }

                    break;
            }
            return true;
        } else {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isDragging = true;
                    if (!isInitialized) {
                        updateBounds();
                    }
                    dX = view.getX() - motionEvent.getRawX();
                    dY = view.getY() - motionEvent.getRawY();
                    if (mOnDragActionListener != null) {
                        mOnDragActionListener.onDragStart(butterfly);
                    }

                    //animation

                    return true;
            }
        }
        return false;
    }

    private void onDragFinish() {
        if(mOnDragActionListener != null) {
            mOnDragActionListener.onDragEnd(butterfly);
        }

        dX = 0;
        dY = 0;

        if(isViewOnTarget())
        {
            butterfly.despawnAnimation();
            basket.collapseBasket(SCALE);
            basket.addToBasket(butterfly.dbField, 1);
        }
    }
}
