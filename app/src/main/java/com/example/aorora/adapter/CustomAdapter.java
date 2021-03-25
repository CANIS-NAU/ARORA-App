package com.example.aorora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.CommunityPage;
import com.example.aorora.R;
import com.example.aorora.interfaces.OnItemClickListener;
import com.example.aorora.interfaces.OnLikeListener;
import com.example.aorora.model.ButterflyLike;
import com.example.aorora.model.QuestReport;
import com.example.aorora.interfaces.OnLikeListener;
import com.example.aorora.model.UserInteraction;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.example.aorora.model.RetroPhoto;
import com.example.aorora.model.Notification;
import com.example.aorora.network.GetDataService;
import com.example.aorora.network.RetrofitClientInstance;
import com.example.aorora.network.NetworkCalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.aorora.MainActivity.user_info;
import static java.lang.Math.min;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private List<Notification> dataList;
    protected boolean item_liked = false;
    private Context context;
    List<Integer> quest_type_ids;
    List<String> usernames;
    List<Integer> user_butterfly_types;
    String[] accomplishment_description;
    GetDataService myService;
    private int indexOfClickedItem = -1;
    //Need to determine which button was pressed inside the specific RecyclerView
    private OnLikeListener myLikeListener;

/*
    public interface OnItemClickListener
    {
        void onItemClick(int position );
    }
*/
    public CustomAdapter(Context context,List<Notification> dataList,
                         List<Integer> quest_type_ids,
                         List<String> usernames,
                         List<Integer> user_butterfly_types,
                         String[] accomplishment_description,
                         OnLikeListener listener
                         ){
        this.context = context;
        this.dataList = dataList;
        this.quest_type_ids = quest_type_ids;
        this.usernames = usernames;
        this.user_butterfly_types = user_butterfly_types;
        this.accomplishment_description = accomplishment_description;
        myLikeListener = listener;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtTitle;
        TextView username_tv;
        TextView time_published;
        ImageView coverImage;
        ImageView like_button;
        //RelativeLayout rv_rel_layout_like_button;

        public CustomViewHolder(View itemView, final OnLikeListener listener)
        {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.notification_content_tv);
            username_tv = itemView.findViewById(R.id.user_name_notification_tv);
            time_published = itemView.findViewById(R.id.time_published_tv);
            coverImage = itemView.findViewById(R.id.coverImage);
            like_button = itemView.findViewById(R.id.like_button);
            myService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        }

        /**
         * Purpose: Will like or un-like the notification the user has specified
         * Algorithm: Takes in the user_id as well as the whole list of ButterflyLikes currently in the DB,
         *            as well as the current amount of QuestReports in order to determine if there is a
         *            server-side and client-side match of a like
         * @param pos : not needed as of this moment, may remove
         */
        boolean bindItem(final int pos )
        {
            final int myUserId = MainActivity.user_info.getUser_id();
            final int questID =dataList.get(dataList.size() -(pos + 1)).getNotification_id();
            Call<List<Notification>> myCall = myService.getUserLikes( myUserId );
            myCall.enqueue(new Callback<List<Notification>>()
                           {
                               @Override
                               public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response)
                               {
                                   boolean isLiked = false, isFound = false;
                                   int likePosition = -1;


                                   if( response.isSuccess() )
                                   {
                                       final List<Notification> likeList = response.body();

                                       for (Notification curLike : likeList)
                                       {
                                           /**
                                           if (!isLiked && ((curLike.getUser_id() == myUserId) && (curLike.getQuestReportId() == questID)))
                                           {
                                               //Check to see if user id and the quest report id are found together
                                               Log.e("FOUND LIKE", " Found the butterfly like.");
                                               isLiked = true;
                                               break;
                                           }*/
                                       }

                                       if (!isLiked)
                                       {
                                           Log.i("ADP UN", "Item " + pos + " is unliked");
                                           like_button.setImageResource(R.drawable.heart_unfilled);
                                           //myLikeButton.setImageResource(R.drawable.heart_unfilled);
                                       } else
                                       {
                                           Log.i("ADP L", "Item " + pos + " is liked");
                                           like_button.setImageResource(R.drawable.heart_filled);
                                           // myLikeButton.setImageResource(R.drawable.heart_filled);
                                       }

                                   }
                               }

                               @Override
                               public void onFailure(Call<List<Notification>> call, Throwable t)
                               {
                               }
                           }
            );

            //Placeholder until I figure out what I need
            return true;
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_comments_row, parent, false);
        CustomViewHolder holder = new CustomViewHolder( view, myLikeListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String desc = accomplishment_description[quest_type_ids.get(position)-1];
        holder.txtTitle.setText(desc);
        holder.bindItem( position );
        Log.e("-----------BND ", " "+position);
        holder.username_tv.setText(usernames.get(position));
        int butterfly_id = user_butterfly_types.get(position);
        switch (butterfly_id){
            case 0:
                holder.coverImage.setImageResource(R.drawable.orange_butterfly_image);
                break;
            case 1:
                holder.coverImage.setImageResource(R.drawable.blue_butterfly_image);
                break;
            case 2:
                holder.coverImage.setImageResource(R.drawable.red_butterfly_image);
                break;
            case 3:
                holder.coverImage.setImageResource(R.drawable.green_butterfly_image);
                break;
            case 4:
                holder.coverImage.setImageResource(R.drawable.yellow_butterfly_image);
                break;
            case 5:
                holder.coverImage.setImageResource(R.drawable.purple_butterfly_image);
                break;
            default:
                holder.coverImage.setImageResource(R.drawable.purple_butterfly_image);
                break;
        }
        holder.like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                toggleLike( position, holder);
                notifyItemChanged( position );
            }
        });
    }



    @Override
    public int getItemCount() {

        return min(dataList.size(),20);
    }


    /**
     * @param position
     * @return the quest report ID at the item's position.
     */
    public int getReceiverId( int position )
    {
        return 0;//dataList.get( dataList.size()-position-1 ).getUser_receiver_id();
    }


    /**
     * @param position
     * @return the quest report ID at the item's position.
     */
    public int getItemQuestId( int position )
    {
        return 0;//dataList.get( dataList.size()-position-1 ).getQuest_record_id();
    }

    /**
     * @param position
     * @return the quest report ID at the item's position.
     */
    public int getUserInteractionId( int position )
    {
        return 0;//dataList.get( dataList.size()-position-1 ).getUser_interaction_id();
    }

    /**
     * Purpose: Will set the like status of the notification in question
     * Algorithm:
     * @param position : The index of the specific item that in the notification layout.
     * @param status  : Tells us whether the specific notification needs a like (false)
     *                  or needs to remove one (true).
     */
    public void setLikeStatus( int position, boolean status, int receiver_id, int interaction_type_id, int secondary_id)
    {
        final int myUserId = MainActivity.user_info.getUser_id();

         //if true, then remove like from DB and update notification at said position
            //else, add like to DB and update notification at said location
         if( !status )
         {
             NetworkCalls.createLike(myUserId, receiver_id, interaction_type_id, secondary_id, "Empty Content");
             Log.i("ADP L CREATED", " "+myUserId+", "+receiver_id);

         }
         else
         {
             if(receiver_id >= 0)
             {

                 NetworkCalls.removeLike(secondary_id);
                 Log.i("ADP L DEL", "" + secondary_id);
             }
             else
             {
                 Log.e("NO LIKE", " Like could not be removed (incorrect index)");
             }
         }
    }

    /**
     * Potentially needs a getViewAtPosition()
     */
    /**
     * When the user likes someone else's automated post that they finished
     * an activity, the button will fill in and the server will record the like.
     *
     * PROGRESS----
     * backend is set up, but un-liking is still not possible at the moment
     */
    protected void toggleLike(final int myPosition, final CustomViewHolder myHolder)
    {
        final int myUserId = MainActivity.user_info.getUser_id();
        final int likeType = 3;
        //final int questID = dataList.get(dataList.size() -(myPosition + 1)).getQuest_record_id();
        //final int receiverID = dataList.get(dataList.size() -(myPosition + 1)).getUser_receiver_id();

        Call<List<Notification>> myCall = myService.getUserLikes( myUserId );
        myCall.enqueue(new Callback<List<Notification>>()
                       {
                           @Override
                           public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response)
                           {
                               boolean isLiked = false, isFound = false;
                               int likePosition = -1;


                               if( response.isSuccess() )
                               {
                                   final List<Notification> likeList = response.body();

                                   for (Notification curLike : likeList)
                                   {
                                    /**
                                       if (!isLiked && (/*Might not need so the (curLike.getUser_id() == myUserId) &&* (curLike.getQuest_record_id() == questID)))
                                       {
                                           //Check to see if user id and the quest report id are found together
                                           Log.e("FOUND LIKE", " Found the butterfly like.");
                                           isLiked = true;
                                           break;
                                       }*/
                                   }

                                   if (!isLiked)
                                   {
                                       /**
                                       Log.i("TGL UN", "Item " + myPosition + " is unliked");
                                       setLikeStatus(myPosition, isLiked, receiverID, likeType, questID);
                                       myHolder.like_button.setImageResource(R.drawable.heart_unfilled);
                                       notifyItemChanged(myPosition);
                                       //myLikeButton.setImageResource(R.drawable.heart_unfilled);
                                        */
                                   } else
                                   {
                                       /**
                                       Log.i("TGL L", "Item " + myPosition + " is liked");
                                       setLikeStatus(myPosition, isLiked, receiverID, likeType, questID);
                                       myHolder.like_button.setImageResource(R.drawable.heart_filled);
                                       notifyItemChanged(myPosition);
                                       // myLikeButton.setImageResource(R.drawable.heart_filled);
                                        */
                                   }

                               }
                           }

                           @Override
                           public void onFailure(Call<List<Notification>> call, Throwable t)
                           {
                           }
                       }
        );
    }
}
