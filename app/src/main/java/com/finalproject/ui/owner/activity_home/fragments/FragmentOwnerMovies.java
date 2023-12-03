package com.finalproject.ui.owner.activity_home.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.finalproject.R;
import com.finalproject.adapter.DayAdapter;
import com.finalproject.adapter.DayOwnerAdapter;
import com.finalproject.adapter.MoviesAdapter;
import com.finalproject.adapter.MoviesFilterAdapter;
import com.finalproject.adapter.TimeOwnerAdapter;
import com.finalproject.databinding.FragmentMoviesBinding;
import com.finalproject.databinding.FragmentOwnerMoviesBinding;
import com.finalproject.model.CategoryModel;
import com.finalproject.model.DayModel;
import com.finalproject.model.MovieModel;
import com.finalproject.model.TimeModel;
import com.finalproject.mvvm.FragmentMoviesMvvm;
import com.finalproject.ui.activity_base.BaseFragment;
import com.finalproject.ui.owner.activity_home.OwnerHomeActivity;
import com.finalproject.ui.user.activity_home.HomeActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FragmentOwnerMovies extends BaseFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private OwnerHomeActivity activity;
    private FragmentOwnerMoviesBinding binding;
    private MoviesFilterAdapter moviesFilterAdapter;
    private MoviesAdapter moviesAdapter;
    private FragmentMoviesMvvm mvvm;
    private BottomSheetBehavior behavior;
    private DayOwnerAdapter dayAdapter;
    private List<DayModel> dayModelList;
    private TimeOwnerAdapter timeOwnerAdapter;
    private List<TimeModel> timeModelList;
    private MovieModel movieModel;
    private String have_not = "";
    private List<MovieModel> movieModelList;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_owner_movies, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        movieModel = new MovieModel();
        movieModelList = new ArrayList<>();
        dayModelList = new ArrayList<>();
        timeModelList = new ArrayList<>();
        behavior = BottomSheetBehavior.from(binding.sheet.root);
        binding.sheet.setLang(getLang());
        mvvm = ViewModelProviders.of(this).get(FragmentMoviesMvvm.class);

//        if (dayModelList.size()>0){
//            binding.sheet.llDays.setVisibility(View.VISIBLE);
//        }else {
//            binding.sheet.llDays.setVisibility(View.GONE);
//        }
//
//        if (timeModelList.size()>0){
//            binding.sheet.llTime.setVisibility(View.VISIBLE);
//        }else {
//            binding.sheet.llTime.setVisibility(View.GONE);
//        }

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

        mvvm.getIsLoading().observe(activity, isLoading -> {
            binding.swipeRef.setRefreshing(isLoading);
        });
        mvvm.getOnCategorySuccess().observe(activity, categoryModels -> {
            if (categoryModels.size() > 0) {
                if (moviesFilterAdapter != null) {
                    moviesFilterAdapter.updateList(categoryModels);
                }
            }
        });
        mvvm.getCategory();
        mvvm.getOnMoviesSuccess().observe(activity, movieModels -> {
            if (movieModels.size() > 0) {
                binding.cardNoData.setVisibility(View.GONE);
                if (moviesAdapter != null) {
                    movieModelList.clear();
                    movieModelList.addAll(movieModels);
                    moviesAdapter.updateList(movieModels);
                }
            } else {
                binding.cardNoData.setVisibility(View.VISIBLE);
                moviesAdapter.updateList(new ArrayList<>());
            }
        });
        mvvm.getMovies(null);
        binding.swipeRef.setOnRefreshListener(() -> {
            if (mvvm.getCategoryId().getValue() != null) {
                mvvm.getMovies(null);
            } else {
                binding.swipeRef.setRefreshing(false);
            }
        });
        moviesFilterAdapter = new MoviesFilterAdapter(activity, this);
        binding.recyclerFilter.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));
        binding.recyclerFilter.setAdapter(moviesFilterAdapter);


        moviesAdapter = new MoviesAdapter(activity, this);
        binding.recyclerMovies.setLayoutManager(new GridLayoutManager(activity, 2));
        binding.recyclerMovies.setAdapter(moviesAdapter);


    }

    public void setItemCategory(CategoryModel categoryModel, int currentPos) {
        mvvm.getCategoryId().setValue(categoryModel.getId());
        mvvm.getMovies(null);
    }

    public void setItemChecked(MovieModel movieModel, int position) {

        openSheet(movieModel, position);


    }

    private void openSheet(MovieModel movieModel, int position) {
        binding.sheet.btnConfirm.setOnClickListener(view -> {
            setHaveMovie(movieModel, position);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        binding.sheet.btnCancel.setOnClickListener(view -> {
            setHaveNotMovie(movieModel, position);
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


    private void setHaveMovie(MovieModel movieModel, int position) {
        have_not = "have";
        movieModel.setHave_or_not(have_not);
        movieModelList.set(position, movieModel);
        moviesAdapter.updateList(movieModelList);

    }

    private void setHaveNotMovie(MovieModel movieModel, int position) {
        have_not = " ";
        movieModel.setHave_or_not(have_not);
        movieModelList.set(position, movieModel);
        moviesAdapter.updateList(movieModelList);
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

    private int getItemSpinnerPos(DayModel dayModel) {
        int pos = -1;
        for (int index = 0; index < dayModelList.size(); index++) {
            DayModel model = dayModelList.get(index);
            if (model.getId() == dayModel.getId()) {
                pos = index;
                return pos;
            }
        }
        return pos;
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