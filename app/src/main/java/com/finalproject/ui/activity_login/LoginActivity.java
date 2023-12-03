package com.finalproject.ui.activity_login;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;

import android.os.Bundle;
import android.widget.Toast;


import com.finalproject.R;
import com.finalproject.databinding.ActivityLoginBinding;
import com.finalproject.model.LoginModel;
import com.finalproject.model.UserModel;
import com.finalproject.mvvm.ActivityLoginMvvm;
import com.finalproject.ui.activity_base.BaseActivity;
import com.finalproject.ui.activity_signup.SignUpActivity;
import com.finalproject.ui.forgetPassword.ForgetPasswordActivity1;
import com.finalproject.ui.owner.activity_home.OwnerHomeActivity;
import com.finalproject.ui.user.activity_home.HomeActivity;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private String type = "";
    private ActivityLoginMvvm mvvm;
    private LoginModel loginModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        mvvm = ViewModelProviders.of(this).get(ActivityLoginMvvm.class);
        loginModel = new LoginModel();
        binding.setModel(loginModel);

        binding.btnCustomer.setOnClickListener(view -> {
            setupbutton1();
        });
        binding.btnOwner.setOnClickListener(view -> {
            setupbutton2();
        });

        mvvm.getOnLoginSuccess().observe(this, userModel -> {
            setUserModel(userModel);
            if (userModel.getData().getType().equals("customer")){
                navigateToUserHome();
            }else if (userModel.getData().getType().equals("owner")){
                navigateToOwnerHome();
            }


        });

        binding.btnLogin.setOnClickListener(view -> {
            if (loginModel.isDataValid(this)) {
                if (!type.isEmpty()) {
                    mvvm.login(this, loginModel, type);
                } else {
                    Toast.makeText(this, R.string.please_choose_the_user, Toast.LENGTH_SHORT).show();
                }
            }


        });
        binding.txtCreate.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        binding.forgetPass.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity1.class);
            startActivity(intent);

        });

    }

    private void navigateToOwnerHome() {
        Intent intent = new Intent(LoginActivity.this, OwnerHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToUserHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void setupbutton1() {
        type = "customer";
        loginModel.setType(type);
        binding.btnCustomer.setBackgroundResource(R.drawable.bg_user_btn_clicked);
        binding.btnOwner.setBackgroundResource(R.drawable.bg_user_btn);
        binding.btnCustomer.setTextColor(getResources().getColor(R.color.black));
        binding.btnOwner.setTextColor(getResources().getColor(R.color.white));

    }

    public void setupbutton2() {
        type = "owner";
        loginModel.setType(type);
        binding.btnOwner.setBackgroundResource(R.drawable.bg_user_btn_clicked);
        binding.btnCustomer.setBackgroundResource(R.drawable.bg_user_btn);
        binding.btnOwner.setTextColor(getResources().getColor(R.color.black));
        binding.btnCustomer.setTextColor(getResources().getColor(R.color.white));


    }

}