package com.finalproject.ui.user.activity_booking_seats;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.finalproject.R;
import com.finalproject.adapter.DayAdapter;
import com.finalproject.adapter.SpinnerTicketTypeAdapter;
import com.finalproject.adapter.TimeAdapter;
import com.finalproject.databinding.ActivityBookingSeatsBinding;
import com.finalproject.databinding.ActivityBookingShowSeatsBinding;
import com.finalproject.databinding.DialogSeatsBinding;
import com.finalproject.model.CinemaModel;
import com.finalproject.model.DayModel;
import com.finalproject.model.MovieModel;
import com.finalproject.model.SeatsModel;
import com.finalproject.model.ShowModel;
import com.finalproject.model.TicketTypeModel;
import com.finalproject.model.TimeModel;
import com.finalproject.mvvm.ActivityBookingSeatsMvvm;
import com.finalproject.ui.activity_base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BookingShowSeatsActivity extends BaseActivity {
    private String lang;
    private ActivityBookingShowSeatsBinding binding;
    private DialogSeatsBinding seatsBinding;
    private ShowModel showModel;
    private CinemaModel cinemaModel;
    private DayModel dayModel;
    private TimeModel timeModel;
    private List<DayModel> dayModelList;
    private List<TimeModel> timeModelList;
    private DayAdapter dayAdapter;
    private TimeAdapter timeAdapter;
    private SpinnerTicketTypeAdapter spinnerTicketTypeAdapter;
    private List<TicketTypeModel> ticketTypeModelList;
    private AlertDialog dialog;
    private ActivityBookingSeatsMvvm mvvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_booking_show_seats);
        getDataFromIntent();
        initView();


    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        showModel = (ShowModel) intent.getSerializableExtra("showModel");
        cinemaModel = (CinemaModel) intent.getSerializableExtra("cinemaModel");

    }
    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.booking_seats), R.color.color2, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> {
            finish();
        });
        ticketTypeModelList = new ArrayList<>();
        ticketTypeModelList.add(0, new TicketTypeModel(getResources().getString(R.string.ticket_type)));
        ticketTypeModelList.add(1, new TicketTypeModel("Normal"));
        ticketTypeModelList.add(2, new TicketTypeModel("VIP"));
        dayModel = new DayModel();
        timeModel = new TimeModel();
        dayModelList = new ArrayList<>();
        timeModelList = new ArrayList<>();
        binding.setShowModel(showModel);
        binding.setCinemaModel(cinemaModel);

        mvvm = ViewModelProviders.of(this).get(ActivityBookingSeatsMvvm.class);

        mvvm.getOnDaySuccess().observe(this, dayModels -> {
            if (dayModels.size() > 0) {
                if (dayAdapter != null) {
                    dayModelList.clear();
                    dayModelList.addAll(dayModels);
                    dayAdapter.updateList(dayModels);
                }
            }
        });
        mvvm.getOnTimeSuccess().observe(this, timeModels -> {
            if (timeModels.size() > 0) {
                if (timeAdapter != null) {
                    timeModelList.clear();
                    timeModelList.addAll(timeModels);
                    timeAdapter.updateList(timeModels);

                }
            }
        });
        mvvm.getDays(cinemaModel.getId(), showModel.getId());
        mvvm.getOnSeatsSuccess().observe(this, seatsModel -> {
            if (seatsModel.getData()!=null){
                seatsBinding.setSeatsModel(seatsModel);
            }
        });


        spinnerTicketTypeAdapter = new SpinnerTicketTypeAdapter(ticketTypeModelList, this);
        binding.spinnerTicketType.setAdapter(spinnerTicketTypeAdapter);
        binding.spinnerTicketType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dayAdapter = new DayAdapter(dayModelList, this);
        timeAdapter = new TimeAdapter(timeModelList, this);
        binding.recViewDay.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recViewTime.setLayoutManager(new GridLayoutManager(this, 3));
        binding.recViewDay.setAdapter(dayAdapter);
        binding.recViewTime.setAdapter(timeAdapter);

        binding.llTime.setEnabled(false);
        binding.llChooseSeats.setEnabled(false);
        binding.llDay.setOnClickListener(view -> openDays());
        binding.llTime.setOnClickListener(view -> openTimes());
        binding.closeDay.setOnClickListener(view -> binding.flDay.setVisibility(View.GONE));
        binding.closeTime.setOnClickListener(view -> binding.flTime.setVisibility(View.GONE));
        binding.tvDone1.setOnClickListener(view -> {
            if (dayModel.getDay() != null) {
                binding.flDay.setVisibility(View.GONE);
                binding.tvDay.setText(dayModel.getDay());
                binding.llTime.setEnabled(true);
                mvvm.getTimes(dayModel);
            }

        });
        binding.tvDone2.setOnClickListener(view -> {
            if (timeModel.getHour() != null) {
                binding.flTime.setVisibility(View.GONE);
                binding.tvTime.setText(timeModel.getHour());
                binding.llChooseSeats.setEnabled(true);
                mvvm.getSeats(cinemaModel,dayModel,timeModel);
            }

        });

        binding.llChooseSeats.setOnClickListener(view -> {
            dialog.show();
        });

        createSeatsDialog();
    }

    private void openDays() {
        binding.flDay.setVisibility(View.VISIBLE);
    }

    private void openTimes() {
        binding.flTime.setVisibility(View.VISIBLE);
    }

    private void createSeatsDialog() {

        dialog = new AlertDialog.Builder(this)
                .create();
        seatsBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_seats, null, false);

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(seatsBinding.getRoot());

        seatsBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        seatsBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    public void setDayItem(DayModel model) {
        binding.tvDone1.setVisibility(View.VISIBLE);
        this.dayModel = model;
    }

    public void setTimeItem(TimeModel model) {
        binding.tvDone2.setVisibility(View.VISIBLE);
        this.timeModel = model;
    }
}
