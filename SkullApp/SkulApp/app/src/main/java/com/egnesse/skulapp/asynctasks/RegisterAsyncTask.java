package com.egnesse.skulapp.asynctasks;

import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;
import com.egnesse.skulapp.constants.NetworkConstnats;
import com.egnesse.skulapp.dto.MessageCustomDialogDTO;
import com.egnesse.skulapp.dto.RegisterDTO;
import com.egnesse.skulapp.ui.Dialog;
import com.egnesse.skulapp.ui.GetProgressBar;
import com.egnesse.skulapp.ui.SnackBar;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by adityaagrawal on 10/02/16.
 */
public class RegisterAsyncTask extends AsyncTask<Void, Void, Void> implements NetworkConstnats{

    private Context context;
    private RegisterDTO registerDTO;
    private MaterialDialog.Builder builder;
    private MaterialDialog dialog;
    private OkHttpClient client = new OkHttpClient();
    private Exception exceptionToBeThrown = null;
    private Response response;

    public RegisterAsyncTask(Context context, RegisterDTO registerDTO) {
        this.context = context;
        this.registerDTO = registerDTO;
        builder = GetProgressBar.get(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = builder.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        RequestBody formBody = new FormBody.Builder().add("data", new Gson().toJson(registerDTO)).build();
        Request request = new Request.Builder().url(REGISTER_URL).post(formBody).build();
        try {
            response = client.newCall(request).execute();
        }catch (Exception e){
            exceptionToBeThrown = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
        if(null != exceptionToBeThrown){
            MessageCustomDialogDTO messageCustomDialogDTO = new MessageCustomDialogDTO();
            messageCustomDialogDTO.setMessage(exceptionToBeThrown.toString());

            SnackBar.show(context, messageCustomDialogDTO);
            Dialog.showSimpleDialog(context, exceptionToBeThrown.toString());
            return;
        }else{
            try {
                Dialog.showSimpleDialog(context, response.body().string());
            }catch (Exception e){
                Dialog.showSimpleDialog(context, e.toString());
            }
        }
    }
}
