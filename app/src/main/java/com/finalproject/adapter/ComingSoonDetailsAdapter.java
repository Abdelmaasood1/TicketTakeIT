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
import com.finalproject.databinding.ComingSoonItemBinding;
import com.finalproject.model.ComingSoonModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ComingSoonDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ComingSoonModel> list;
    private Context context;
    private LayoutInflater inflater;


    public ComingSoonDetailsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ComingSoonItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.coming_soon_item, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));
        //myHolder.binding.image.setImageResource(R.drawable.spider);
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
        ComingSoonItemBinding binding;

        public MyHolder(ComingSoonItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
