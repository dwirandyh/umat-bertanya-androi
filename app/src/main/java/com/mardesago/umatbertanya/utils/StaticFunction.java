package com.mardesago.umatbertanya.utils;

import com.google.android.material.snackbar.Snackbar;
import android.view.View;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class StaticFunction {

    static final String BASE_URL = "http://umatbertanya.dwirandyh.com/";

    public static void showSnackBar(View view, CharSequence charSequence) {
        Snackbar snackbar = Snackbar
                .make(view, charSequence, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showSnackBar(View view, CharSequence charSequence, View.OnClickListener onClickListener){
        Snackbar snackbar = Snackbar
                .make(view, charSequence, Snackbar.LENGTH_INDEFINITE)
                .setAction("Ulangi", onClickListener);
        snackbar.show();
    }
}
