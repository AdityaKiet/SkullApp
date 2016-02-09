package com.egnesse.skulapp.activities;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egnesse.skulapp.R;
import com.egnesse.skulapp.dto.MessageCustomDialogDTO;
import com.egnesse.skulapp.security.Validate;
import com.egnesse.skulapp.ui.SnackBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    @Bind(R.id.etEmail)
    AutoCompleteTextView autoCompleteTextViewEmail;
    @Bind(R.id.etPassword)
    EditText etPassword;
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private TextView txtTitle;
    @OnClick(R.id.txtForgotPassword)
    void forgotPassword(){

    }
    @OnClick(R.id.txtSignUp)
    void signUp(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.btnLogin)
    void login() {
        attemptLogin();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        populate();

    }

    private void populate() {
        ButterKnife.bind(this);
        populateAutoComplete();
        setSupportActionBar(toolbar);

        // txtTitle.setText();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLogin.setBackgroundResource(R.drawable.abc_alpha_btn_background_ripple);
        }

        getSupportActionBar().setTitle(getResources().getString(R.string.login));

        toolbar.setNavigationIcon(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    public void attemptLogin() {

        autoCompleteTextViewEmail.setError(null);
        etPassword.setError(null);

        String email = autoCompleteTextViewEmail.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;


        if (!Validate.isValidEmailAddress(email)) {
            autoCompleteTextViewEmail.setError(getString(R.string.error_field_required));
            focusView = autoCompleteTextViewEmail;
            cancel = true;
            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_field_required));
            SnackBar.show(this, messageCustomDialogDTO);

        } else if (!Validate.isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;

            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_invalid_password));
            SnackBar.show(this, messageCustomDialogDTO);
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE},
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {ContactsContract.CommonDataKinds.Email.ADDRESS, ContactsContract.CommonDataKinds.Email.IS_PRIMARY,};
        int ADDRESS = 0;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
        autoCompleteTextViewEmail.setAdapter(adapter);
    }


}

