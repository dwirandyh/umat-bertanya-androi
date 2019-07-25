package com.mardesago.umatbertanya.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardesago.umatbertanya.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.mardesago.umatbertanya.activity.BiografiActivity;
import com.mardesago.umatbertanya.model.imam;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class BiografiDialog extends BottomSheetDialog implements View.OnClickListener {

    @BindView(R.id.txt_nama)
    TextView txtNama;
    @BindView(R.id.txt_keterangan)
    TextView txtKeterangan;
    @BindView(R.id.foto)
    ImageView foto;

    @BindView(R.id.txt_read_more)
    TextView txtReadMore;

    Context context;

    imam data;


    public BiografiDialog(@NonNull Context context, int theme, imam data) {
        super(context, theme);
        this.context = context;
        this.data = data;
    }

    public BiografiDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_biografi, null);
        setContentView(bottomSheetView);

        ButterKnife.bind(this, bottomSheetView);
        configureBottomSheetBehavior(bottomSheetView);

        txtNama.setText(data.getNama());
        txtKeterangan.setText(data.getKeterangan().replaceAll("[^a-zA-Z0-9]", " ").substring(0, 200));

        /*
        Glide
                .with(bottomSheetView)
                .load(StaticFunction.BASE_URL + "img/imam/" + data.getId_imam() + ".jpg")
                .apply(RequestOptions.centerCropTransform())
                .into(foto);
                */

        txtReadMore.setOnClickListener(this);
    }

    private void configureBottomSheetBehavior(View contentView) {
        BottomSheetBehavior mBottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());

        if (mBottomSheetBehavior != null) {
            mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    //showing the different states
                    switch (newState) {
                        case BottomSheetBehavior.STATE_HIDDEN:
                            dismiss(); //if you want the modal to be dismissed when user drags the bottomsheet down
                            break;
                        case BottomSheetBehavior.STATE_EXPANDED:
                            break;
                        case BottomSheetBehavior.STATE_COLLAPSED:
                            break;
                        case BottomSheetBehavior.STATE_DRAGGING:
                            break;
                        case BottomSheetBehavior.STATE_SETTLING:
                            break;
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_read_more:
                Intent intent = new Intent(context, BiografiActivity.class);
                intent.putExtra("imam", data);
                context.startActivity(intent);
                break;
        }
    }
}
