package com.finalproject.ui.activity_contact_us;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.Toast;

import com.finalproject.R;
import com.finalproject.databinding.ActivityContactUsBinding;
import com.finalproject.model.ContactUsModel;
import com.finalproject.model.UserModel;
import com.finalproject.mvvm.ActivityContactUsMvvm;
import com.finalproject.preferences.Preferences;
import com.finalproject.ui.activity_base.BaseActivity;

public class ContactUsActivity extends BaseActivity {
    private ActivityContactUsBinding binding;
    private ActivityContactUsMvvm mvvm;
    private ContactUsModel contactUsModel;
    private UserModel userModel;
    private Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us);
        initView();
    }

    private void initView() {
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(this);
        setUpToolbar(binding.toolbar, getString(R.string.contactUs), R.color.color2, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());
        mvvm= ViewModelProviders.of(this).get(ActivityContactUsMvvm.class);
        mvvm.send.observe(this, aBoolean -> {
            if (aBoolean) {
                Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.sucess), Toast.LENGTH_LONG).show();
                finish();
            }
        });
        contactUsModel = new ContactUsModel();
        if (userModel!=null){
            contactUsModel.setName(userModel.getData().getName());
            contactUsModel.setMail(userModel.getData().getEmail());
        }
        binding.setModel(contactUsModel);


        binding.btnSend.setOnClickListener(view -> {

            if (contactUsModel.isDataValid(this)) {
                mvvm.contactWithUs(this, contactUsModel);
            }
        });

    }
}