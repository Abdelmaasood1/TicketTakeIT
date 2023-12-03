package com.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.CastItemBinding;
import com.finalproject.model.HeroModel;
import com.finalproject.model.MovieModel;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<HeroModel> heroList ;
    private Context context;
    private LayoutInflater inflater;

    public CastAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CastItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.cast_item, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder=(MyHolder)holder;
        myHolder.binding.setHero(heroList.get(position));

    }

    @Override
    public int getItemCount() {
        if (heroList != null) {
            return heroList.size();
        } else {
            return 0;
        }
    }

    public void updateList(List<HeroModel> heroList) {
        this.heroList = heroList;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public CastItemBinding binding;
        public MyHolder( CastItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
