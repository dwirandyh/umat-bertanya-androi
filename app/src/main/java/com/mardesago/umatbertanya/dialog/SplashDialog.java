package com.mardesago.umatbertanya.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mardesago.umatbertanya.R;

/**
 * Created by Febrian Reza on 22-Nov-17.
 */

public class SplashDialog extends Dialog {


    public SplashDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        setContentView(R.layout.dialog_splash);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 3000);
    }
}
