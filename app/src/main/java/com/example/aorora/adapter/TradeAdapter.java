package com.example.aorora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aorora.MainActivity;
import com.example.aorora.R;
import com.example.aorora.SuperflyGamePage;
import com.example.aorora.model.SuperflyInvite;
import com.example.aorora.model.SuperflySession;
import com.example.aorora.model.TradeRequest;
import com.example.aorora.model.UserInfo;
import com.example.aorora.network.NetworkCalls;
import com.example.aorora.network.RetrofitResponseListener;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeAdapter extends RecyclerView.Adapter<TradeAdapter.TradeAdapterViewHolder> {
    Context context;
    TradeRequestActionListener tradeRequestListener;
    //List of displayed trade requests
    ArrayList<TradeRequest> currRequests;
    String[] butterfly_count_names;

    //This will take in the names, descs, and images to be held in our recyclerview.
    public TradeAdapter(Context ct, ArrayList<TradeRequest> inputRequests){
        this.context = ct;
        this.currRequests = inputRequests;
        this.butterfly_count_names = new String[]{"user_b0_count","user_b1_count",
                "user_b2_count","user_b3_count","user_b4_count"};
    }


    @NonNull
    @Override
    public TradeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trade_request_row, parent, false);
        TradeAdapterViewHolder tradeViewHolder = new TradeAdapterViewHolder(view);
        return tradeViewHolder;
    }
    //TODO: Why is this position flag giving so much trouble? I suppressed unnecessary warnings and
    //declared final to use in the onclicklistener.
    @Override
    public void onBindViewHolder(@NonNull TradeAdapterViewHolder tradeViewHolder, @SuppressLint("RecyclerView") final int position) {
        tradeViewHolder.requester.setText(currRequests.get(position).getSender().getUser_name());
        tradeViewHolder.redCount.setText(currRequests.get(position).getB0_requested().toString());
        tradeViewHolder.yellowCount.setText(currRequests.get(position).getB1_requested().toString());
        tradeViewHolder.violetCount.setText(currRequests.get(position).getB2_requested().toString());
        tradeViewHolder.greenCount.setText(currRequests.get(position).getB3_requested().toString());
        tradeViewHolder.blueCount.setText(currRequests.get(position).getB4_requested().toString());
        tradeViewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TradeRequest currRequest = currRequests.get(position);
                //Accept and trade the request.
                if(!canAcceptTrade(currRequests.get(position))){
                    Toast.makeText(context, "Not enough butterflies to trade!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Otherwise we can trade with enough butterflies in our inventory.
                Map<String, Integer> oldSenderAtrium = new HashMap<>();
                Map<String, Integer> updatedSenderAtrium = new HashMap<>();
                Map<String, Integer> oldRecipientAtrium = new HashMap<>();
                Map<String, Integer> updatedRecipientAtrium = new HashMap<>();
                for(int i = 0; i < 5; i++){
                    String getterName = "getB" + i + "_requested";
                    String countKey = "user_b" + i + "_count";

                    try {
                        //Get the current method for the ith count of butterfly in the traderequest
                        Method method = TradeRequest.class.getMethod(getterName);
                        //Call the method to get the amount requested for this type.
                        Object currPart = method.invoke(currRequest);
                        Integer countTraded = (Integer) currPart;
                        //Build a local atrium to use for the original sender.
                        currRequest.getSender().build_atrium();
                        oldSenderAtrium = currRequest.getSender().get_local_atrium();
                        oldRecipientAtrium = MainActivity.user_info.get_local_atrium();
                        //Add the requested butterflies to the original sender's atrium
                        updatedSenderAtrium.put(countKey, oldSenderAtrium.get(countKey) + countTraded);
                        //Then SUBTRACT them from the reciever (i.e. the provider of the butterflies)
                        updatedRecipientAtrium.put(countKey, oldRecipientAtrium.get(countKey) - countTraded);

                        Log.d("Onclick request", "Old sender Atrium" + oldSenderAtrium.toString());
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        Log.d("getNextRound", "No method found");
                        e.printStackTrace();
                    }

                }
                NetworkCalls.updateUserAtrium(MainActivity.user_info.getUser_id(), updatedRecipientAtrium, context, new RetrofitResponseListener() {
                    @Override
                    public void onSuccess() {
                        NetworkCalls.updateUserAtrium(currRequest.getUid_sender(), updatedSenderAtrium, context, new RetrofitResponseListener() {
                            @Override
                            public void onSuccess() {
                                NetworkCalls.deleteTradeRequestById(currRequest.getRequest_id(), context, new RetrofitResponseListener() {
                                    @Override
                                    public void onSuccess() {
                                        //If all calls work, update locally
                                        MainActivity.user_info.update_local_atrium(updatedRecipientAtrium);
                                        Toast.makeText(context, "Trade successful!", Toast.LENGTH_SHORT).show();
                                        tradeRequestListener.requestCompleted();
                                    }

                                    @Override
                                    public void onFailure() {

                                    }
                                });

                            }

                            @Override
                            public void onFailure() {
                                //IF either one FAILS, rollback. TODO
                                Toast.makeText(context, "Trade failed due to network error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });

        tradeViewHolder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TradeRequest currRequest = currRequests.get(position);
                //Decline and delete the request
                Toast.makeText(context, "Declining", Toast.LENGTH_SHORT).show();
                NetworkCalls.deleteTradeRequestById(currRequest.getRequest_id(), context, new RetrofitResponseListener() {
                    @Override
                    public void onSuccess() {
                        Log.d("Decline Button", "Deleted request, please refresh");
                        tradeRequestListener.requestCompleted();

                    }

                    @Override
                    public void onFailure() {
                        Log.d("Decline Button", "Delete request FAILED!");
                    }
                });

            }
        });


    }

    boolean canAcceptTrade(TradeRequest currentRequest){
        UserInfo user = MainActivity.user_info;
        return user.getUser_b0_count() >= currentRequest.getB0_requested() &&
                user.getUser_b1_count() >= currentRequest.getB1_requested() &&
                user.getUser_b2_count() >= currentRequest.getB2_requested() &&
                user.getUser_b3_count() >= currentRequest.getB3_requested() &&
                user.getUser_b4_count() >= currentRequest.getB4_requested();
    }

    @Override
    public int getItemCount() {
        return currRequests.size();
    }

    public interface TradeRequestActionListener {
        public void requestCompleted();
    }

    public void setTradeRequestActionListener(TradeRequestActionListener newListener){
        this.tradeRequestListener = newListener;
    }

    public class TradeAdapterViewHolder extends RecyclerView.ViewHolder{
        TextView requester;
        TextView redCount;
        TextView yellowCount;
        TextView violetCount;
        TextView greenCount;
        TextView blueCount;
        Button acceptBtn;
        Button declineBtn;
        public TradeAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            requester = itemView.findViewById(R.id.requesting_user_tv);
            redCount = itemView.findViewById(R.id.red_needed_tv);
            yellowCount = itemView.findViewById(R.id.yellow_needed_tv);
            violetCount = itemView.findViewById(R.id.violet_needed_tv);
            greenCount = itemView.findViewById(R.id.green_needed_tv);
            blueCount = itemView.findViewById(R.id.blue_needed_tv);
            acceptBtn = itemView.findViewById(R.id.accept_btn);
            declineBtn = itemView.findViewById(R.id.decline_btn);

        }

    }
}