package com.example.aorora;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.example.aorora.butterflyGame.Basket;
import com.example.aorora.butterflyGame.Butterfly;
import com.example.aorora.butterflyGame.ButterflyBasketOnDragListener;
import com.example.aorora.network.NetworkCalls;

import java.util.Random;

/**
 * ButterflyGame Activity contains the butterfly catching game component of the ARORA app.
 * the game will start with just a "bucket" view on screen as well a text view displaying the
 * remaining time. once the timer is complete the view will intent to the atrium to show collected
 * butterflies.
 */
public class ButterflyGameActivity extends AppCompatActivity {

    //FIXME: add back button override
    //FIXME: add pause override

    Context butterflyGameContext;

    ConstraintLayout layout;
    ConstraintLayout.LayoutParams params;

    //30 seconds
    final private int MAX_TIMER_MILLISECONDS = 30000;

    Basket basket;

    Butterfly bView;

    private TextView gameTimeText;

    private long timeLeftInMilliseconds = MAX_TIMER_MILLISECONDS; //30 seconds

    private Random seed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterfly_game);

        butterflyGameContext = this;

        layout = (ConstraintLayout) findViewById(R.id.layout);
        basket = (Basket) findViewById(R.id.basket);
        gameTimeText = (TextView) findViewById(R.id.gameTimeText);

        seed = new Random();

        //GAME LIFECYCLE
        CountDownTimer gameTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {

                //update text in view to remaining time
                gameTimeText.setText(String.valueOf(l/1000));

                //try to spawn extra butterfly at any given second (1/4 chance)
                if(seed.nextInt() % 4 == 0)
                {
                    createRandomButterfly();
                }

                //spawn butterfly every 5 seconds after the start and not on zero
                if(timeLeftInMilliseconds/1000 != 30 &&
                        timeLeftInMilliseconds/1000 % 5 == 0 &&
                        timeLeftInMilliseconds/1000 <= 1 )
                {
                    createRandomButterfly();
                }
            }


            @Override
            public void onFinish() {

                endOfGameActions();

            }
        }.start();

    }

    private void endOfGameActions() {

        //TODO update user info table local
        MainActivity.user_info.update_local_atrium(basket.basketContents);

        //TODO update network db with hashmap?
        NetworkCalls.updateUserAtrium(MainActivity.user_info.getUser_id(),
                            basket.basketContents, getApplicationContext());

        //TODO implement atrium screen code
        startActivity(new Intent(butterflyGameContext, AtriumScreen.class));
        finish();
    }

    /**
     * creates a butterfly with a random common type, random x value within
     * the layouts bounds, random y value within layout bounds.
     */
    private void createRandomButterfly() {
        int typeID = seed.nextInt(Butterfly.Type.getCount());
        int x = seed.nextInt() % layout.getWidth();
        int y = seed.nextInt() % layout.getHeight();
        createButterfly( Butterfly.Type.valueOf(typeID), x, y);
    }


    /**
     * creates a butterfly view and adds it to the layout and starts the butterflies
     * animation to move around the screen. also adds an ButterflyBasketOnDragListener
     * to the butterfly.
     * @param type Butterfly.Type that will define the type of butterfly.
     * @param x coordinate for butterfly to be added to layout at
     * @param y coordinate for butterfly to be added to layout at
     * @return a reference to the butterfly added to layout.
     */
    private Butterfly createButterfly(Butterfly.Type type, float x, float y) {

        bView = new Butterfly(this, type);

        //adding constraint layout (neede to add view to layout)
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //width = 120dp
        float width = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 120,
                this.getResources().getDisplayMetrics() );
        newParams.width = (int) width;

        //height = 86dp
        float height = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 86,
                this.getResources().getDisplayMetrics() );
        newParams.height = (int) height;

        //set view coordinates
        bView.setX(x);
        bView.setY(y);

        layout.addView(bView, newParams);
        bView.spawnAnimation();

        //add listener to view assuming basket exists already
        bView.setOnTouchListener(new ButterflyBasketOnDragListener(bView, basket));

        //FIXME: be able to get width and height of parent view without bugs
        bView.animateToRandomPoints(720, 1472);

        return bView;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus) {
            hideSystemUI();
        }
    }

    /**
     * allows for activity to be fullscreen
     */
    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    private void showSystemUI()
    {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }
}
