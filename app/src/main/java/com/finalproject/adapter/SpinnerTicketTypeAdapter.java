package com.finalproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.finalproject.R;
import com.finalproject.databinding.SpinnerTicketTypeRowBinding;
import com.finalproject.model.TicketTypeModel;

import java.util.List;

public class SpinnerTicketTypeAdapter extends BaseAdapter {
    private List<TicketTypeModel> modelList;
    private Context context;

    public SpinnerTicketTypeAdapter(List<TicketTypeModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (modelList==null){
            return 0;
        }else {
            return modelList.size();
        }

    }

    @Override
    public Object getItem(int i) {
        return modelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") SpinnerTicketTypeRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.spinner_ticket_type_row,viewGroup,false);
        binding.setTitle(modelList.get(i).getTitle());
        return binding.getRoot();
    }

    public void updateList(List<TicketTypeModel> modelList){
        if (modelList!=null){
            this.modelList = modelList;
        }
        notifyDataSetChanged();
    }
}
