package com.example.aorora.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aorora.R;
import com.example.aorora.model.RetroPhoto;

import java.util.List;

public class PollenShopAdapter extends RecyclerView.Adapter<PollenShopAdapter.PollenShopItemHolder> {

    private List<RetroPhoto> dataList;
    private Context context;

    public PollenShopAdapter(Context context,List<RetroPhoto> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class PollenShopItemHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView shop_item_image;
        TextView shop_item_desc;
        TextView item_name;
        TextView item_price;

        private ImageView coverImage;

        PollenShopItemHolder(View itemView) {
            super(itemView);
            mView = itemView;

            shop_item_image = (ImageView) mView.findViewById(R.id.pollen_shop_item_image);
            shop_item_desc = (TextView) mView.findViewById(R.id.pollen_shop_item_desc_tv);
            item_name = (TextView) mView.findViewById(R.id.pollen_shop_item_name_tv);
            item_price = (TextView) mView.findViewById(R.id.pollen_shop_item_price);
        }
    }

    @NonNull
    @Override
    public PollenShopAdapter.PollenShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.pollen_shop_item_row, parent, false);
        return new PollenShopAdapter.PollenShopItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PollenShopAdapter.PollenShopItemHolder holder, int position) {
        //holder.txtTitle.setText(dataList.get(position).getTitle());
        //holder.coverImage.setImageResource(R.drawable.orange_butterfly_image);
        if(position == 1)
        {
            holder.item_name.setText("Appeareance Token");
            holder.shop_item_desc.setText("This token can be used to unlock a new appearence variant of the desired butterfly");
            holder.shop_item_image.setImageResource(R.drawable.token);
        }        /*
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
        return 2;
    }
}
