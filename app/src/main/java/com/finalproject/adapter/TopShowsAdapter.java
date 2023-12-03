package com.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.TopShowsItemRowBinding;
import com.finalproject.model.MovieModel;
import com.finalproject.model.ShowModel;
import com.finalproject.ui.user.activity_home.fragments.FragmentHome;

import java.util.List;

public class TopShowsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ShowModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public TopShowsAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TopShowsItemRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.top_shows_item_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));

        myHolder.itemView.setOnClickListener(view -> {
            FragmentHome fragmentHome = (FragmentHome) fragment;
            fragmentHome.navigateToShowDetailsActivity(list.get(myHolder.getAbsoluteAdapterPosition()),myHolder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<ShowModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public TopShowsItemRowBinding binding;

        public MyHolder(TopShowsItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
