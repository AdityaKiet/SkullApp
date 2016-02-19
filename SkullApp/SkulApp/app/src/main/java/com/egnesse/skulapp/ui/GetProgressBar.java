package com.egnesse.skulapp.ui;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.egnesse.skulapp.R;

/**
 * Created by adityaagrawal on 10/02/16.
 */
public class GetProgressBar {

    public static MaterialDialog.Builder get(Context context) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .content(R.string.please_wait).cancelable(false).progress(true, 0);
        return builder;
    }
}
