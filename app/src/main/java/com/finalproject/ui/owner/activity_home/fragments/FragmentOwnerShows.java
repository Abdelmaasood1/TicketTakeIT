package com.finalproject.ui.owner.activity_home.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.finalproject.R;
import com.finalproject.adapter.DayOwnerAdapter;
import com.finalproject.adapter.ShowsAdapter;
import com.finalproject.adapter.TimeOwnerAdapter;
import com.finalproject.databinding.FragmentOwnerShowsBinding;
import com.finalproject.databinding.FragmentShowsBinding;
import com.finalproject.model.DayModel;
import com.finalproject.model.ShowModel;
import com.finalproject.model.TimeModel;
import com.finalproject.mvvm.FragmentShowMVVM;
import com.finalproject.ui.activity_base.BaseFragment;
import com.finalproject.ui.owner.activity_home.OwnerHomeActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentOwnerShows extends BaseFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener
{
    private OwnerHomeActivity activity;
    private FragmentOwnerShowsBinding binding;
    private ShowsAdapter showsAdapter;
    private FragmentShowMVVM mvvm;
    private BottomSheetBehavior behavior;
    private DayOwnerAdapter dayAdapter;
    private List<DayModel> dayModelList;
    private TimeOwnerAdapter timeOwnerAdapter;
    private List<TimeModel> timeModelList;
    private String have_not = "";
    private List<ShowModel> showModelList;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String date = null;


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (OwnerHomeActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_shows, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }
    private void initView() {
        showModelList = new ArrayList<>();
        dayModelList = new ArrayList<>();
        timeModelList = new ArrayList<>();
        behavior = BottomSheetBehavior.from(binding.sheet.root);
        binding.sheet.setLang(getLang());

        mvvm = ViewModelProviders.of(this).get(FragmentShowMVVM.class);

        binding.sheet.llChooseDay.setOnClickListener(view -> datePickerDialog.show(activity.getFragmentManager(), ""));

        createDateDialog();

        binding.sheet.llChooseTime.setEnabled(false);
        binding.sheet.llChooseTime.setOnClickListener(view -> timePickerDialog.show(activity.getFragmentManager(), ""));

        createTimeDialog();

        dayAdapter = new DayOwnerAdapter(dayModelList, this, activity);
        binding.sheet.recViewDays.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false));
        binding.sheet.recViewDays.setAdapter(dayAdapter);

        timeOwnerAdapter = new TimeOwnerAdapter(timeModelList, this, activity);
        binding.sheet.recViewTime.setLayoutManager(new GridLayoutManager(activity, 2, LinearLayoutManager.HORIZONTAL, false));
        binding.sheet.recViewTime.setAdapter(timeOwnerAdapter);

        showsAdapter = new ShowsAdapter(activity, this);
        binding.recyclerShows.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recyclerShows.setAdapter(showsAdapter);

        mvvm.getIsLoading().observe(activity, isLoading -> binding.swipeRef.setRefreshing(isLoading));

        mvvm.getShowsSuccess().observe(activity, showModels -> {
            if (showModels.size() > 0) {
                binding.cardNoData.setVisibility(View.GONE);
                if (showsAdapter != null) {
                    showModelList.clear();
                    showModelList.addAll(showModels);
                    showsAdapter.updateList(showModels);
                }
            } else {
                binding.cardNoData.setVisibility(View.VISIBLE);
                showsAdapter.updateList(new ArrayList<>());
            }
        });

        mvvm.getShowData(activity,null);

        binding.swipeRef.setOnRefreshListener(() -> mvvm.getShowData(activity,null));

        showsAdapter = new ShowsAdapter(activity, this);
        binding.recyclerShows.setLayoutManager(new GridLayoutManager(activity,2));
        binding.recyclerShows.setAdapter(showsAdapter);


    }



    public void setItemChecked(ShowModel showModel, int position) {
        openSheet(showModel, position);
    }

    private void openSheet(ShowModel showModel, int position) {
        binding.sheet.btnConfirm.setOnClickListener(view -> {
            setHaveMovie(showModel, position);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        binding.sheet.btnCancel.setOnClickListener(view -> {
            setHaveNotMovie(showModel, position);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            dayModelList.clear();
            timeModelList.clear();
            dayAdapter.updateList(dayModelList);
            timeOwnerAdapter.updateList(timeModelList);
            binding.sheet.tvDate.setText("");
            binding.sheet.tvHour.setText("");
        });

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setDraggable(false);
    }

    private void setHaveMovie(ShowModel showModel, int position) {
        have_not = "have";
        showModel.setHave_or_not(have_not);
        showModelList.set(position, showModel);
        showsAdapter.updateList(showModelList);

    }

    private void setHaveNotMovie(ShowModel showModel, int position) {
        have_not = " ";
        showModel.setHave_or_not(have_not);
        showModelList.set(position, showModel);
        showsAdapter.updateList(showModelList);
    }

    private void createDateDialog() {

        Calendar calendar = Calendar.getInstance();
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.dismissOnPause(true);
        datePickerDialog.setAccentColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ActivityCompat.getColor(activity, R.color.gray12));
        datePickerDialog.setOkColor(ActivityCompat.getColor(activity, R.color.colorPrimary));

        datePickerDialog.setOkText(getString(R.string.select));
        datePickerDialog.setCancelText(getString(R.string.cancel));
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);

    }
    private void createTimeDialog() {

        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.dismissOnPause(true);

        timePickerDialog.setAccentColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(activity, R.color.gray4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(activity, R.color.colorPrimary));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);


    }

    @Override
    public void onDateSet(DatePickerDialog datePicker, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        binding.sheet.tvDate.setText(date);
        DayModel dayModel = new DayModel(date);
        if (dayModel.getDay()!=null){
            binding.sheet.llChooseTime.setEnabled(true);
        }

        boolean inList = isItemInDayList(dayModel);
        if (!inList) {
            dayModelList.add(0, dayModel);
            dayAdapter.updateList(dayModelList);
        } else {
            Toast.makeText(activity, "Day added before", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

//        calendar.set(Calendar.SECOND, second);

        String time = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(calendar.getTime());

        binding.sheet.tvHour.setText(time);
        TimeModel timeModel = new TimeModel(time);
        boolean inList = isItemInTimeList(timeModel);

        if (!inList) {
            timeModelList.add(0, timeModel);
            timeOwnerAdapter.updateList(timeModelList);
        } else {
            Toast.makeText(activity, "Time added before", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSelectedDay(int adapterPosition) {
        dayModelList.remove(adapterPosition);
        dayAdapter.notifyItemRemoved(adapterPosition);
    }

    public void deleteSelectedTime(int adapterPosition) {
        timeModelList.remove(adapterPosition);
        timeOwnerAdapter.notifyItemRemoved(adapterPosition);
    }

    private boolean isItemInDayList(DayModel dayModel) {
        for (DayModel model : dayModelList) {
            if (dayModel.getDay().equals(model.getDay())) {
                return true;
            }
        }
        return false;
    }

    private boolean isItemInTimeList(TimeModel timeModel) {
        for (TimeModel model : timeModelList) {
            if (timeModel.getHour().equals(model.getHour())) {
                return true;
            }
        }
        return false;
    }

}