package com.example.aorora.adapter;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.support.annotation.NonNull;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.aorora.R;
        import com.example.aorora.interfaces.OnItemClickListener;

public class HorizontalMountainAdapter extends RecyclerView.Adapter<HorizontalMountainAdapter.HorizontalViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public HorizontalMountainAdapter(Context context, OnItemClickListener listener){
        this.context = context;
        onItemClickListener = listener;
    }
    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        private ImageView coverImage;

        HorizontalViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtTitle = mView.findViewById(R.id.feather_name_tv);
            coverImage = mView.findViewById(R.id.cover_image_feather);
        }
    }
    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_horizontal_rv_item, parent, false);
        return new HorizontalMountainAdapter.HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder horizontalViewHolder, @SuppressLint("RecyclerView") final int position) {
        Log.e("POSITION", "" + position);
        horizontalViewHolder.txtTitle.setVisibility(View.INVISIBLE);
        horizontalViewHolder.coverImage.setAlpha(0.3f);
        switch (position){
            case 1:
                horizontalViewHolder.coverImage.setImageResource(R.drawable.blue_mountain);
                horizontalViewHolder.txtTitle.setText("Evening");
                break;
            case 2:
                horizontalViewHolder.coverImage.setImageResource(R.drawable.green_mountain);
                horizontalViewHolder.txtTitle.setText("Afternoon");
                break;
            case 3:
                horizontalViewHolder.coverImage.setImageResource(R.drawable.orange_mountain);
                horizontalViewHolder.txtTitle.setText("Morning");
                break;
            default:
                horizontalViewHolder.coverImage.setVisibility(View.INVISIBLE);
                horizontalViewHolder.txtTitle.setText("");
                break;
        }

        if(horizontalViewHolder.coverImage != null)
        {
            horizontalViewHolder.coverImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
