package com.example.aorora.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aorora.R;

import java.util.List;

public class HorizontalTimeAdapter extends RecyclerView.Adapter<HorizontalTimeAdapter.HorizontalTimeViewHolder>{

    private Context context;
    private List<String> data;
    private int data_size;
    public HorizontalTimeAdapter(Context context, List<String> data){
        this.context = context;
        this.data = data;
        data_size = data.size()-1;
    }
    public class HorizontalTimeViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;

        HorizontalTimeViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.minute_horizontal_tv);
        }
    }
    @NonNull
    @Override
    public HorizontalTimeAdapter.HorizontalTimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_time_layout_tv, parent, false);
        return new HorizontalTimeAdapter.HorizontalTimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalTimeAdapter.HorizontalTimeViewHolder horizontalTimeViewHolder, final int position) {
        final int data_size = getItemCount();
        if(data.get(position) == "")
        {
            horizontalTimeViewHolder.txtTitle.setVisibility(View.INVISIBLE);
        }
        else
        {
            horizontalTimeViewHolder.txtTitle.setText("" + data.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
