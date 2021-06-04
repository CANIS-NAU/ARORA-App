package com.example.aorora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aorora.AtriumDetail;
import com.example.aorora.R;

public class AtriumAdapter extends RecyclerView.Adapter<AtriumAdapter.AtriumViewHolder> {
    Context context;
    int imgs[];
    int counts[];

    //This will take in the names, descs, and images to be held in our recyclerview.
    public AtriumAdapter(Context ct, int inImg[], int inCounts[]){
        imgs = inImg;
        counts = inCounts;
        context = ct;
    }


    @NonNull
    @Override
    public AtriumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.atrium_card, parent, false);
        AtriumViewHolder atriumViewHolder = new AtriumViewHolder(view);
        return atriumViewHolder;
    }
    //TODO: Why is this position flag giving so much trouble? I suppressed unnecessary warnings and
    //declared final to use in the onclicklistener.
    @Override
    public void onBindViewHolder(@NonNull AtriumViewHolder atriumAdapterHolder, @SuppressLint("RecyclerView") final int position) {
        atriumAdapterHolder.butterflyImage.setImageResource(imgs[position]);
        atriumAdapterHolder.butterflyCount.setText(Integer.toString(counts[position]));
        atriumAdapterHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CLICKIGN ATRIUM", "Clicked imagebutton in atrium");

                Intent intent = new Intent(context, AtriumDetail.class);
                intent.putExtra("image", imgs[position]);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgs.length;
    }

    public class AtriumViewHolder extends RecyclerView.ViewHolder{
        ImageView butterflyImage;
        TextView butterflyCount;
        CardView cardView;
        public AtriumViewHolder(@NonNull View itemView) {
            super(itemView);
            butterflyImage = itemView.findViewById(R.id.butterfly_img);
            butterflyCount = itemView.findViewById(R.id.butterfly_count);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
