package com.egnesse.skulapp.network;

import android.content.Context;
import android.content.Intent;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.egnesse.skulapp.R;
import com.egnesse.skulapp.activities.EmailVerificationPendingActivity;
import com.egnesse.skulapp.constants.NetworkConstnats;
import com.egnesse.skulapp.dto.ErrorDTO;
import com.egnesse.skulapp.dto.RegisterDTO;
import com.egnesse.skulapp.global.AppController;
import com.egnesse.skulapp.ui.GetProgressBar;
import com.egnesse.skulapp.ui.SnackBar;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by adityaagrawal on 10/02/16.
 */
public class Register implements NetworkConstnats {
    private MaterialDialog.Builder builder;
    private MaterialDialog dialog;

    public void run(final Context context, RegisterDTO registerDTO) throws  Exception{
        builder = GetProgressBar.get(context);
        String url = REGISTER_URL;
        builder.content("Loading...");
        dialog = builder.show();
        final Gson gson = new Gson();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(gson.toJson(registerDTO)), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                dialog.dismiss();
                Intent intent = new Intent(context, EmailVerificationPendingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();

                if(error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError){
                    SnackBar.showSimple(context, context.getResources().getString(R.string.no_internet));
                    return;
                }

                try {
                    String errorString = new String(error.networkResponse.data);
                    JSONObject jsonObject = new JSONObject(errorString);
                    ErrorDTO errorDTO = gson.fromJson(jsonObject.getString("error"), ErrorDTO.class);
                    SnackBar.showSimple(context, errorDTO.getMessage());
                }catch (Exception e){
                    e.printStackTrace();
                    SnackBar.showSimple(context, e.getMessage());
                }

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, AppController.JSON_OBJECT_REQUEST);

    }
}
