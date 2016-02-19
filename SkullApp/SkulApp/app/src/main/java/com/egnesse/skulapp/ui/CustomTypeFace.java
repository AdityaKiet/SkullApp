package com.egnesse.skulapp.ui;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by adityaagrawal on 13/02/16.
 */
public class CustomTypeFace {
    public static Typeface getTypeface(Context context){
        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/Whitney-Book-Bas.otf");
        return face;
    }
}
