package com.finalproject.ui.activity_edit_account;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.finalproject.R;
import com.finalproject.databinding.ActivityEditAccountBinding;
import com.finalproject.model.EditAccountModel;
import com.finalproject.model.UserModel;
import com.finalproject.mvvm.ActivitySignupMvvm;
import com.finalproject.preferences.Preferences;
import com.finalproject.share.Common;
import com.finalproject.tags.Tags;
import com.finalproject.ui.activity_base.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class EditAccountActivity extends BaseActivity {
    private ActivityEditAccountBinding binding;
    private UserModel userModel;
    private EditAccountModel model;
    private String gender = "";
    private ActivitySignupMvvm mvvm;
    private Preferences preferences;
    private ActivityResultLauncher<Intent> launcher;
    private final String READ_PERM = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;
    private final int READ_REQ = 1, CAMERA_REQ = 2;
    private int selectedReq = 0;
    private Uri uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_account);
        initView();
    }

    private void initView() {
        setUpToolbar(binding.toolbar, getString(R.string.editAccount), R.color.color2, R.color.white);
        binding.toolbar.llBack.setOnClickListener(view -> finish());

        mvvm = ViewModelProviders.of(this).get(ActivitySignupMvvm.class);
        binding.setLang(getLang());
        preferences = Preferences.getInstance();
        model = new EditAccountModel();
        userModel = getUserModel();
        model.setName(userModel.getData().getName());
        model.setNational_id(userModel.getData().getNational_id());
        model.setEmail(userModel.getData().getEmail());
        model.setUser_name(userModel.getData().getUser_name());
        if (userModel.getData().getGender().equals("male")) {
            setupMale();
        } else if (userModel.getData().getGender().equals("female")) {
            setupFemale();
        }
        if (userModel.getData().getImage()!=null){
            String url = userModel.getData().getImage();
            Picasso.get().load(Uri.parse(Tags.base_url+url)).into(binding.image);
            binding.icon.setVisibility(View.GONE);
        }
        binding.llMale.setOnClickListener(view -> {
            setupMale();
        });
        binding.llFemale.setOnClickListener(view -> {
            setupFemale();
        });
        model.setGender(gender);
        binding.setModel(model);
        binding.setUserModel(userModel);

        mvvm.getOnSignUpSuccess().observe(this, userModel -> {
            setUserModel(userModel);
            setResult(RESULT_OK);
            finish();
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                if (selectedReq == READ_REQ) {
                    binding.icon.setVisibility(View.GONE);
                    uri = result.getData().getData();

                    File file = new File(Common.getImagePath(this, uri));
                    Picasso.get().load(file).fit().into(binding.image);

                } else if (selectedReq == CAMERA_REQ) {
                    Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                    binding.icon.setVisibility(View.GONE);
                    uri = getUriFromBitmap(bitmap);
                    if (uri != null) {
                        String path = Common.getImagePath(this, uri);
                        if (path != null) {
                            Picasso.get().load(new File(path)).fit().into(binding.image);

                        } else {
                            Picasso.get().load(uri).fit().into(binding.image);

                        }
                    }
                }
            }
        });

        binding.flGallery.setOnClickListener(view -> {
            closeSheet();
            checkReadPermission();
        });
        binding.flCamera.setOnClickListener(view -> {
            closeSheet();
            checkCameraPermission();
        });
        binding.btnCancel.setOnClickListener(view -> closeSheet());

        binding.image.setOnClickListener(view -> openSheet());

        ProgressDialog dialog = new ProgressDialog(this);
        binding.btnUpdate.setOnClickListener(view -> {
//            Log.e("data",signUpModel.getType()+" "+ signUpModel.getNational_id()+" "+signUpModel.getName()+" "+signUpModel.getEmail()+" "+signUpModel.getUser_name()+" "+signUpModel.getPassword()+" "+signUpModel.getGender());
                if (model.isDataValid(this)){
                    mvvm.update(this,model,userModel);
                }

        });


    }

    private void setupMale() {
        binding.llMale.setBackgroundResource(R.drawable.small_stroke_color3);
        binding.llFemale.setBackgroundResource(R.drawable.bg_user_btn_not_clicked);
        gender = "male";
        model.setGender(gender);
    }

    private void setupFemale() {
        binding.llFemale.setBackgroundResource(R.drawable.small_stroke_color3);
        binding.llMale.setBackgroundResource(R.drawable.bg_user_btn_not_clicked);
        gender = "female";
        model.setGender(gender);
    }

    public void checkCameraPermission() {

        closeSheet();

        if (ContextCompat.checkSelfPermission(this, write_permission) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, camera_permission) == PackageManager.PERMISSION_GRANTED
        ) {
            SelectImage(CAMERA_REQ);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{camera_permission, write_permission}, CAMERA_REQ);
        }
    }

    private void SelectImage(int req) {
        selectedReq = req;
        Intent intent = new Intent();

        if (req == READ_REQ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            } else {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            launcher.launch(intent);

        } else if (req == CAMERA_REQ) {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                launcher.launch(intent);
            } catch (SecurityException e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }


        }
    }

    public void openSheet() {
        binding.expandLayout.setExpanded(true, true);
    }

    public void closeSheet() {
        binding.expandLayout.collapse(true);

    }

    public void checkReadPermission() {
        closeSheet();
        if (ActivityCompat.checkSelfPermission(this, READ_PERM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PERM}, READ_REQ);
        } else {
            SelectImage(READ_REQ);
        }
    }

    private Uri getUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        return Uri.parse(MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "", ""));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_REQ) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA_REQ) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                SelectImage(requestCode);
            } else {
                Toast.makeText(this, getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
            }
        }
    }


}