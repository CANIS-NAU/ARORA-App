package com.example.aorora.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.aorora.R;
import com.example.aorora.model.RetroPhoto;

import java.util.List;

public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.QuestViewHolder>{

    private List<RetroPhoto> dataList;
    private Context context;

    public QuestAdapter(Context context,List<RetroPhoto> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class QuestViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView quest_image;
        TextView quest_desc_tv;
        TextView possible_pollens_tv;
        ProgressBar quest_progress_bar;
        TextView percentage_completed;

        private ImageView coverImage;

        QuestViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            quest_image = (ImageView) mView.findViewById(R.id.quest_image);
            quest_desc_tv = (TextView) mView.findViewById(R.id.quest_description_tv);
            possible_pollens_tv = (TextView) mView.findViewById(R.id.possible_pollens_from_quest_tv);
            quest_progress_bar= (ProgressBar) mView.findViewById(R.id.quest_progress_bar);
            percentage_completed = (TextView) mView.findViewById(R.id.completed_quest_percentage_tv);
        }
    }

    @Override
    public QuestAdapter.QuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_quest_layout, parent, false);
        return new QuestAdapter.QuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        //holder.txtTitle.setText(dataList.get(position).getTitle());
        //holder.coverImage.setImageResource(R.drawable.orange_butterfly_image);
        int max = holder.quest_progress_bar.getMax();
        double prog = Math.random()*max;
        holder.quest_progress_bar.setProgress((int)prog);
        /*
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.orange_butterfly_button))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);
                */
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
