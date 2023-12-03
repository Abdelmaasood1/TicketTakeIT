package com.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.ComingSoonHomeItemBinding;
import com.finalproject.model.ComingSoonModel;


import java.util.List;

public class ComingSoonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ComingSoonModel> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public ComingSoonAdapter(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ComingSoonHomeItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.coming_soon_home_item, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setComingSoonModel(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<ComingSoonModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ComingSoonHomeItemBinding binding;

        public MyHolder(ComingSoonHomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}
