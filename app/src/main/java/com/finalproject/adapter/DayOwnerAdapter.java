package com.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.DayOwnerRowBinding;
import com.finalproject.databinding.DayRowBinding;
import com.finalproject.model.DayModel;
import com.finalproject.ui.owner.activity_home.fragments.FragmentOwnerMovies;
import com.finalproject.ui.owner.activity_home.fragments.FragmentOwnerShows;
import com.finalproject.ui.user.activity_booking_seats.BookingSeatsActivity;
import com.finalproject.ui.user.activity_booking_seats.BookingShowSeatsActivity;

import java.util.List;

public class DayOwnerAdapter extends RecyclerView.Adapter<DayOwnerAdapter.MyHolder> {

    private List<DayModel> dayList;
    private Context context;
    private Fragment fragment;
    private LayoutInflater inflater;

    public DayOwnerAdapter(List<DayModel> dayList, Fragment fragment, Context context) {
        this.dayList = dayList;
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DayOwnerRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.day_owner_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(dayList.get(position));
        if (fragment instanceof FragmentOwnerMovies) {
            FragmentOwnerMovies fragmentOwnerMovies = (FragmentOwnerMovies) fragment;
            myHolder.binding.imageClose.setVisibility(View.VISIBLE);
        } else if (fragment instanceof FragmentOwnerShows) {
            FragmentOwnerShows fragmentOwnerShows = (FragmentOwnerShows) fragment;
            myHolder.binding.imageClose.setVisibility(View.VISIBLE);
        }
        myHolder.binding.imageClose.setOnClickListener(view -> {
            if (fragment instanceof FragmentOwnerMovies) {
                FragmentOwnerMovies fragmentOwnerMovies = (FragmentOwnerMovies) fragment;
                fragmentOwnerMovies.deleteSelectedDay(myHolder.getAdapterPosition());
            } else if (fragment instanceof FragmentOwnerShows) {
                FragmentOwnerShows fragmentOwnerShows = (FragmentOwnerShows) fragment;
                fragmentOwnerShows.deleteSelectedDay(myHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (dayList == null)
            return 0;
        else
            return dayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private DayOwnerRowBinding binding;

        public MyHolder(@NonNull DayOwnerRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void updateList(List<DayModel> list) {
        if (list != null) {
            this.dayList = list;
        }
        notifyDataSetChanged();
    }
}
