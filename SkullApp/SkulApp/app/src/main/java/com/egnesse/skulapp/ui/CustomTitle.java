package com.egnesse.skulapp.ui;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;

/**
 * Created by adityaagrawal on 13/02/16.
 */
public class CustomTitle {
    public static SpannableString getTitle(Context context, String title){
        SpannableString s = new SpannableString(title);
        s.setSpan(new com.egnesse.skulapp.ui.TypefaceSpan(context, "Whitney-Semibold-Bas.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return s;
    }
}
