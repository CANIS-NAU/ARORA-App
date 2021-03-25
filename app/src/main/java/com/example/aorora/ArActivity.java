package com.example.aorora;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class ArActivity extends AppCompatActivity {

    ArFragment arFragment;

    private int selectedModelId;
    private ModelRenderable loadedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_ar_fragment);
        arFragment = (ArFragment) getSupportFragmentManager()
            .findFragmentById(R.id.fragment);
        selectedModelId = R.raw.caterpiller;
    }

    private void loadModel() {
        ModelRenderable
                .builder()
                .setSource(this, selectedModelId)
                .build()
                .thenAccept(modelRenderable -> {
                    Toast.makeText(this, "Model Built", Toast.LENGTH_SHORT).show();
                    loadedModel = modelRenderable;
                });
    }

    private void addNodeToScene(ModelRenderable loadedModel){
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {

            AnchorNode anchorNode = new AnchorNode(hitResult.createAnchor());
            anchorNode.setRenderable(loadedModel);
            arFragment.getArSceneView().getScene().addChild(anchorNode);

        }));
    }
}