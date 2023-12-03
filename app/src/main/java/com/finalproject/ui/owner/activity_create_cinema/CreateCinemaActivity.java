package com.finalproject.ui.owner.activity_create_cinema;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.finalproject.R;
import com.finalproject.databinding.ActivityCreateCinemaBinding;
import com.finalproject.model.CreateCinemaModel;
import com.finalproject.model.LocationModel;
import com.finalproject.mvvm.ActivityMapMvvm;
import com.finalproject.ui.activity_base.BaseActivity;
import com.finalproject.ui.owner.activity_home.OwnerHomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateCinemaActivity extends BaseActivity implements OnMapReadyCallback {
    private ActivityCreateCinemaBinding binding;
    private GoogleMap mMap;
    private float zoom = 15.0f;
    private LocationModel locationmodel;
    private ActivityMapMvvm activitymapMvvm;
    private ActivityResultLauncher<String> permissionLauncher;
    private CreateCinemaModel createCinemaModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_create_cinema);
        initView();
    }

    private void initView() {
        createCinemaModel=new CreateCinemaModel();
        binding.btnCreate.setOnClickListener(view -> {
            Intent intent=new Intent(CreateCinemaActivity.this, OwnerHomeActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }

    private void addMarker(double lat, double lng) {
        if (activitymapMvvm.getGoogleMap().getValue() != null) {
            mMap = activitymapMvvm.getGoogleMap().getValue();
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoom));

    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, BaseActivity.fineLocPerm) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(BaseActivity.fineLocPerm);
        } else {

            activitymapMvvm.initGoogleApi();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {

            activitymapMvvm.startLocationUpdate();

        }
    }
}