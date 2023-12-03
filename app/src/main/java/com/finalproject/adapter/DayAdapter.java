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
import com.finalproject.databinding.DayRowBinding;
import com.finalproject.model.DayModel;
import com.finalproject.ui.owner.activity_home.fragments.FragmentOwnerMovies;
import com.finalproject.ui.owner.activity_home.fragments.FragmentOwnerShows;
import com.finalproject.ui.user.activity_booking_seats.BookingSeatsActivity;
import com.finalproject.ui.user.activity_booking_seats.BookingShowSeatsActivity;

import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyHolder>{

    private List<DayModel> dayList;
    private Context context;
    private int currentPos = 0;
    private int oldPos = currentPos;
    private RecyclerView.ViewHolder oldHolder;
    private Fragment fragment;
    private String lang;
    private LayoutInflater inflater;

    public DayAdapter(List<DayModel> dayList, Context context) {
        this.dayList = dayList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DayRowBinding dayRowBinding= DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.day_row,parent,false);
        return new MyHolder(dayRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        MyHolder myHolder=(MyHolder) holder;
        myHolder.dayRowBinding.setModel(dayList.get(position));
        if (oldHolder==null){
            oldHolder=myHolder;
        }
        myHolder.itemView.setOnClickListener(view -> {
            if (oldHolder!=null){
                DayModel oldDay=dayList.get(oldPos);
                oldDay.setSelected(false);
                dayList.set(oldPos,oldDay);

                MyHolder oHolder=(MyHolder) oldHolder;
                oHolder.dayRowBinding.setModel(oldDay);
            }
            currentPos=myHolder.getAdapterPosition();
            DayModel model=dayList.get(currentPos);
            model.setSelected(true);
            dayList.set(currentPos,model);
            myHolder.dayRowBinding.setModel(model);

            oldHolder=myHolder;
            oldPos=currentPos;
            if (context instanceof BookingSeatsActivity){
                BookingSeatsActivity activity=(BookingSeatsActivity) context;
                activity.setDayItem(model);
            }else if (context instanceof BookingShowSeatsActivity){
                BookingShowSeatsActivity activity=(BookingShowSeatsActivity) context;
                activity.setDayItem(model);
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
        private DayRowBinding dayRowBinding;

        public MyHolder(@NonNull DayRowBinding dayRowBinding) {
            super(dayRowBinding.getRoot());
            this.dayRowBinding = dayRowBinding;
        }
    }
    public void updateList(List<DayModel> list) {
        if (list != null) {
            this.dayList = list;
        }
        notifyDataSetChanged();
    }
}
