package com.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.CinemaItemBinding;
import com.finalproject.model.CinemaModel;
import com.finalproject.ui.user.activity_cinema_users.CinemasUserActivity;
import com.finalproject.ui.user.activity_cinema_users.CinemasUserShowActivity;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CinemaUsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CinemaModel> cinemaList;
    private Context context;
    private String lang;
    private LayoutInflater inflater;


    public CinemaUsersAdapter(Context context,String lang) {
        this.context = context;
        this.lang=lang;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CinemaItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.cinema_item, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(cinemaList.get(position));
        myHolder.binding.setLang(lang);

        myHolder.itemView.setOnClickListener(view -> {
            if (context instanceof CinemasUserActivity){
                CinemasUserActivity activity=(CinemasUserActivity) context;
                activity.navigateToBookingActivity(cinemaList.get(position),position);
            }else if (context instanceof CinemasUserShowActivity){
                CinemasUserShowActivity activity=(CinemasUserShowActivity) context;
                activity.navigateToBookingActivity(cinemaList.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cinemaList != null) {
            return cinemaList.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<CinemaModel> list) {
        this.cinemaList = list;
        notifyDataSetChanged();
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public CinemaItemBinding binding;

        public MyHolder(CinemaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
