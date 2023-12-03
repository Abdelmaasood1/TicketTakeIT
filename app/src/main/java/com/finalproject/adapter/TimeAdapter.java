package com.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.R;
import com.finalproject.databinding.TimeRowBinding;
import com.finalproject.model.TimeModel;
import com.finalproject.ui.user.activity_booking_seats.BookingSeatsActivity;
import com.finalproject.ui.user.activity_booking_seats.BookingShowSeatsActivity;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.MyHolder>{
    private List<TimeModel> timeModelList;
    private Context context;
    private int currentPos = 0;
    private int oldPos = currentPos;
    private RecyclerView.ViewHolder oldHolder;

    public TimeAdapter(List<TimeModel> timeModelList, Context context) {
        this.timeModelList = timeModelList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TimeRowBinding timeRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.time_row, parent, false);
        return new TimeAdapter.MyHolder(timeRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        myHolder.timeRowBinding.setTime(timeModelList.get(position));
        if (oldHolder == null) {
            oldHolder = myHolder;
        }
        myHolder.itemView.setOnClickListener(view -> {

            if (oldHolder!=null){
                TimeModel oldTimeModel = timeModelList.get(oldPos);
                oldTimeModel.setSelected(false);
                timeModelList.set(oldPos, oldTimeModel);
                MyHolder oHolder = (MyHolder) oldHolder;
                oHolder.timeRowBinding.setTime(oldTimeModel);
            }
            currentPos=myHolder.getAdapterPosition();
            TimeModel model=timeModelList.get(currentPos);
            model.setSelected(true);

            timeModelList.set(currentPos,model);
            myHolder.timeRowBinding.setTime(model);

            oldHolder=myHolder;
            oldPos=currentPos;

            if (context instanceof BookingSeatsActivity){
                BookingSeatsActivity activity=(BookingSeatsActivity) context;
                activity.setTimeItem(model);
            } else if (context instanceof BookingShowSeatsActivity){
                BookingShowSeatsActivity activity=(BookingShowSeatsActivity) context;
                activity.setTimeItem(model);
            }

        });
    }

    @Override
    public int getItemCount() {
        if (timeModelList == null)
            return 0;
        else
            return timeModelList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TimeRowBinding timeRowBinding;

        public MyHolder(@NonNull TimeRowBinding timeRowBinding) {
            super(timeRowBinding.getRoot());
            this.timeRowBinding = timeRowBinding;
        }
    }
    public void updateList(List<TimeModel> list) {
        if (list != null) {
            this.timeModelList = list;
        }
        notifyDataSetChanged();
    }
}
