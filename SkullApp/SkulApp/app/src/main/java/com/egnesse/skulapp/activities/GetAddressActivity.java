package com.egnesse.skulapp.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.egnesse.skulapp.R;
import com.egnesse.skulapp.dto.AddressDTO;
import com.egnesse.skulapp.dto.MessageCustomDialogDTO;
import com.egnesse.skulapp.dto.RegisterDTO;
import com.egnesse.skulapp.network.Register;
import com.egnesse.skulapp.security.Validate;
import com.egnesse.skulapp.ui.Dialog;
import com.egnesse.skulapp.ui.SnackBar;
import com.egnesse.skulapp.util.NetworkCheck;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adityaagrawal on 10/02/16.
 */
public class GetAddressActivity extends AppCompatActivity {
    private RegisterDTO registerDTO;
    @Bind(R.id.etCity)
    EditText etCity;

    @Bind(R.id.etStreet)
    EditText etStreet;

    @Bind(R.id.etState)
    EditText etState;

    @Bind(R.id.etPincode)
    EditText etPincode;

    @Bind(R.id.btnSignUp)
    Button btnSignUp;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @OnClick(R.id.btnSignUp)
    void login() {
        attemptSignUp();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_address);

        registerDTO = new Gson().fromJson(getIntent().getExtras().getString("registerDTO"), RegisterDTO.class);
        populate();
    }


    private void populate() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        // txtTitle.setText();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnSignUp.setBackgroundResource(R.drawable.abc_alpha_btn_background_ripple);
        }

        getSupportActionBar().setTitle(getResources().getString(R.string.address));

        toolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void attemptSignUp() {

        etCity.setError(null);
        etState.setError(null);
        etStreet.setError(null);
        etPincode.setError(null);

        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        String street = etStreet.getText().toString();
        String pincode = etPincode.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!Validate.isStreetValid(street)) {
            etStreet.setError(getString(R.string.error_field_required));
            focusView = etStreet;
            cancel = true;

            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_field_required));
            SnackBar.show(this, messageCustomDialogDTO);

        } else if (!Validate.isCityValid(city)) {
            etCity.setError(getString(R.string.error_field_required));
            focusView = etCity;
            cancel = true;

            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_field_required));
            SnackBar.show(this, messageCustomDialogDTO);

        } else if (!Validate.isStateValid(state)) {
            etState.setError(getString(R.string.error_field_required));
            focusView = etState;
            cancel = true;

            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_field_required));
            SnackBar.show(this, messageCustomDialogDTO);
        } else if (!Validate.isPincodeValid(pincode)) {
            etPincode.setError(getString(R.string.error_invalid_pincode));
            focusView = etPincode;
            cancel = true;

            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_invalid_pincode));
            SnackBar.show(this, messageCustomDialogDTO);
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(street);
            addressDTO.setCity(city);
            addressDTO.setState(state);
            addressDTO.setPincode(pincode);
            registerDTO.setAddress(addressDTO);

            /*RegisterAsyncTask registerAsyncTask = new RegisterAsyncTask(this, registerDTO);
          *//*  if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
                registerAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else*//*
                registerAsyncTask.execute();*/
            if(!NetworkCheck.isNetworkAvailable(this)){
                MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
                messageCustomDialogDTO.setMessage(getString(R.string.no_internet));
                SnackBar.show(this, messageCustomDialogDTO);
                return;
            }

            try{
                Register register = new Register();
                register.run(this, registerDTO);
            }catch (Exception e){
                Dialog.showSimpleDialog(this, e.toString());
            }
        }
    }
}
