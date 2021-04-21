package com.example.aorora;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.TypedValue;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.aorora.butterflyGame.Basket;
import com.example.aorora.butterflyGame.Butterfly;
import com.example.aorora.butterflyGame.ButterflyBasketOnDragListener;
import com.example.aorora.network.NetworkCalls;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * ButterflyGame Activity contains the butterfly catching game component of the ARORA app.
 * the game will start with just a "bucket" view on screen as well a text view displaying the
 * remaining time. once the timer is complete the view will intent to the atrium to show collected
 * butterflies.
 */
public class ButterflyGameActivity extends AppCompatActivity {

    Context mContext;

    ConstraintLayout layout;

    /**
     *  default max time for in game time: set to 30 seconds
     */
    private final int MAX_TIMER_MILLISECONDS = 30000;

    /**
     * Request code for requesting camera permissions
     * reference Request Code Permissions on Android website for further detail
     */
    private final int REQUEST_CODE_PERMISSIONS = 1001;

    /**
     * String array filled with needed permission to request camera access
     * for further permissions just add permission strings to array
     */
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};

    /**
     * Basket where users drag butterflies to collect them
     */
    private Basket basket;

    /**
     * PreviewView for camera background option
     * necessary in order to show camera feed on screen
     */
    private PreviewView mPreviewView;

    /**
     * Switch that controls whether the background is the camera view or not
     * if permission is not granted for camera switch will switch to off
     */
    private Switch cameraSwitch;

    /**
     * Once camera is initialized this flag will prevent it from being reinitialized
     */
    private boolean cameraInitFlag = false;

    /**
     * Displays remaining in game time on screen
     */
    private TextView gameTimeText;

    /**
     * time left in milliseconds starting at default max time
     */
    private long timeLeftInMilliseconds = MAX_TIMER_MILLISECONDS;

    private CountDownTimer gameTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterfly_game);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mContext = this;

        layout = findViewById(R.id.layout);
        basket = findViewById(R.id.basket);
        mPreviewView = findViewById(R.id.previewView);
        cameraSwitch = findViewById(R.id.cameraSwitch);
        gameTimeText = findViewById(R.id.gameTimeText);

        cameraSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    if (cameraPermissionsGranted()) {

                        if(!cameraInitFlag)
                        {
                            startCamera(); //start camera if permission has been granted by user
                        } else {
                            mPreviewView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        ActivityCompat.requestPermissions((Activity) mContext, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                } else {
                    mPreviewView.setVisibility(View.INVISIBLE);
                }
            }
        });

        startGame();

    }

    private void startGame() {

        Random seed = new Random();

        //GAME LIFECYCLE
        gameTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long l) {

                timeLeftInMilliseconds = timeLeftInMilliseconds - 1000;

                //update text in view to remaining time
                gameTimeText.setText(String.valueOf(l / 1000));

                //try to spawn extra butterfly at any given second (1/4 chance)
                if (seed.nextInt() % 4 == 0) {
                    createRandomButterfly();
                }

                //spawn butterfly every 5 seconds after the start and not on zero
                if (timeLeftInMilliseconds / 1000 != 30 &&
                        timeLeftInMilliseconds / 1000 % 5 == 0 &&
                        timeLeftInMilliseconds / 1000 <= 1) {
                    createRandomButterfly();
                }
            }

            @Override
            public void onFinish() {
                endOfGameActions();
            }

        }.start();
    }

    /**
     * creates a butterfly with a random common type, random x value within
     * the layouts bounds, random y value within layout bounds.
     */
    private void createRandomButterfly() {

        Random seed = new Random();

        int typeID = seed.nextInt(Butterfly.Type.getCount());
        int x = seed.nextInt(720);
        int y = seed.nextInt(1000);
        createButterfly(Butterfly.Type.valueOf(typeID), x, y);
    }

    /**
     * creates a butterfly view and adds it to the layout and starts the butterflies
     * animation to move around the screen. also adds an ButterflyBasketOnDragListener
     * to the butterfly.
     *
     * @param type Butterfly.Type that will define the type of butterfly.
     * @param x    coordinate for butterfly to be added to layout at
     * @param y    coordinate for butterfly to be added to layout at
     * @return a reference to the butterfly added to layout.
     */
    private Butterfly createButterfly(Butterfly.Type type, float x, float y) {

        Butterfly mButterfly = new Butterfly(this, type);

        //adding constraint layout (neede to add view to layout)
        ConstraintLayout.LayoutParams newParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT);

        //width = 120dp
        float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120,
                this.getResources().getDisplayMetrics());
        newParams.width = (int) width;

        //height = 86dp
        float height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 86,
                this.getResources().getDisplayMetrics());
        newParams.height = (int) height;

        //set view coordinates
        mButterfly.setX(x);
        mButterfly.setY(y);

        layout.addView(mButterfly, newParams);
        mButterfly.spawnAnimation();

        //add listener to view assuming basket exists already
        mButterfly.setOnTouchListener(new ButterflyBasketOnDragListener(mButterfly, basket));

        //FIXME: be able to get width and height of parent view without bugs
        mButterfly.animateToRandomPoints(720, 1472);

        return mButterfly;
    }

    /**
     * updates user local atrium table with butterflies from basket
     * attempts to update network table with butterflies from basket
     * startActivity to move to atrium screen
     * TODO: instead of going to atrium screen have a fragment pop up instead.
     */
    private void endOfGameActions() {

        // update user info table local
        MainActivity.user_info.update_local_atrium(basket.basketContents);

        // update network db with hash map
        NetworkCalls.updateUserAtrium(MainActivity.user_info.getUser_id(),
                basket.basketContents, getApplicationContext());

        // implement atrium screen code
        startActivity(new Intent(mContext, AtriumScreen.class));
        finish();
    }

    /**
     * initializes ProcessCameraProvider and once the provider is given, bind the preview to the
     * camera view.
     */
    private void startCamera() {
        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);
                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));

        cameraInitFlag = true;
    }

    /**
     * bindPreview attaches all components needed to build our camera and its preview
     * and binds them to he camera provider lifecycle
     *
     * for more information on this reference https://developer.android.com/training/camerax
     * @param cameraProvider provides system camera for cameraX functions
     */
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        // Query if extension is available (optional).
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            // Enable the extension if available.
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }

        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());
        mPreviewView.setVisibility(View.VISIBLE);

        Camera camera = cameraProvider.bindToLifecycle
                (
                        this,
                        cameraSelector,
                        preview
                );
    }

    /**
     * checks if each permission is granted in the required permissions string array
     * @return true if all permissions are granted. false if else
     */
    private boolean cameraPermissionsGranted() {

        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (cameraPermissionsGranted()) {
                startCamera();
                cameraSwitch.setChecked(true);
            } else {
                cameraSwitch.setChecked(false);
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    @Override
    public void onBackPressed() {
        promptLeaveActivity();
    }

    private void promptLeaveActivity(){
        new AlertDialog.Builder(mContext)
                .setMessage("Are you sure you want to leave the game?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endOfGameActions();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
}
