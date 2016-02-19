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
import android.widget.TextView;
import android.widget.Toast;

import com.egnesse.skulapp.R;
import com.egnesse.skulapp.dto.MessageCustomDialogDTO;
import com.egnesse.skulapp.security.Validate;
import com.egnesse.skulapp.ui.CustomTitle;
import com.egnesse.skulapp.ui.CustomTypeFace;
import com.egnesse.skulapp.ui.SnackBar;
import com.egnesse.skulapp.ui.autocompletetextview.CustomAutoCompleteTextView;
import com.egnesse.skulapp.ui.button.ButtonPlus;
import com.egnesse.skulapp.ui.edittext.CustomEditText;

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
    CustomAutoCompleteTextView autoCompleteTextViewEmail;
    @Bind(R.id.etPassword)
    CustomEditText etPassword;
    @Bind(R.id.btnLogin)
    ButtonPlus btnLogin;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.txtForgotPassword)
    TextView txtForgotPassword;
    @Bind(R.id.txtSignUp)
    TextView txtSignUp;

    @OnClick(R.id.txtForgotPassword)
    void forgotPassword() {

    }

    @OnClick(R.id.txtSignUp)
    void signUp() {
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

        txtForgotPassword.setTypeface(CustomTypeFace.getTypeface(this));
        txtSignUp.setTypeface(CustomTypeFace.getTypeface(this));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            btnLogin.setBackgroundResource(R.drawable.abc_alpha_btn_background_ripple);
        }

        getSupportActionBar().setTitle(CustomTitle.getTitle(this, getResources().getString(R.string.login)));

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


        if (!Validate.isValidEmailAddress(email)) {
            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_field_required));
            SnackBar.show(this, messageCustomDialogDTO);

        } else if (!Validate.isPasswordValid(password)) {
            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(getString(R.string.error_invalid_password));
            SnackBar.show(this, messageCustomDialogDTO);
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

