package com.finalproject.adapter;

import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.OwnerHistoryRowBinding;

import java.util.List;

public class OwnerHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> list;
    private Context context;
    private LayoutInflater inflater;
    private Fragment fragment;

    public OwnerHistoryAdapter(Context context,Fragment fragment) {
        this.context = context;
        this.fragment=fragment;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OwnerHistoryRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.owner_history_row, parent, false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.movieName.setText("SpiderMan");
        myHolder.binding.llHeader.setOnClickListener(view -> {
            if (!myHolder.binding.expand.isExpanded()) {
                TransitionManager.beginDelayedTransition(myHolder.binding.cardItem, new AutoTransition());
                myHolder.binding.arrowClicked.setImageResource(R.drawable.ic_top);
                myHolder.binding.expand.setExpanded(true);
            } else {
                TransitionManager.beginDelayedTransition(myHolder.binding.cardItem, new AutoTransition());
                myHolder.binding.arrowClicked.setImageResource(R.drawable.ic_down);
                myHolder.binding.expand.collapse(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 5;
        }
    }

    public void updateList(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public OwnerHistoryRowBinding binding;

        public MyHolder(OwnerHistoryRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}

