package com.mardesago.umatbertanya.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.daftarbacaan;
import com.mardesago.umatbertanya.model.mazhab;
import com.mardesago.umatbertanya.utils.StaticFunction;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class AddBookmarkMazhabDialog extends BottomSheetDialog implements View.OnClickListener {

    private final Realm realm;
    @BindView(R.id.layout_add_bookmark)
    LinearLayout layoutAddBookmark;

    Context context;
    private AlertDialog.Builder alertDialog;

    private mazhab data = new mazhab();

    private View parentView;

    public AddBookmarkMazhabDialog(@NonNull Context context, int theme, mazhab mazhab, View view) {
        super(context, theme);
        this.context = context;
        realm = Realm.getDefaultInstance();
        this.data = mazhab;
        this.parentView = view;
    }

    public AddBookmarkMazhabDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        realm = Realm.getDefaultInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_add_bookmark, null);
        setContentView(bottomSheetView);

        ButterKnife.bind(this, bottomSheetView);
        configureBottomSheetBehavior(bottomSheetView);

        layoutAddBookmark.setOnClickListener(this);
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
            case R.id.layout_add_bookmark:
                /*
                final EditText input = new EditText(context);
                input.setSingleLine();
                final FrameLayout container = new FrameLayout(context);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
                params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
                input.setLayoutParams(params);
                container.addView(input);

                alertDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogMaterialStyle)
                        .setTitle("Nama Daftar Bacaan")
                        .setView(container)
                        .setPositiveButton("OK", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                                final String url = input.getText().toString();
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Number currentId = realm.where(daftarbacaan.class).max("id_daftar");
                                        int nextId = (currentId == null) ? 1 : currentId.intValue() + 1;
                                        daftarbacaan daftarbacaan = realm.createObject(daftarbacaan.class, nextId);
                                        daftarbacaan.setId_mazhab(data.getIdMazhab());
                                        daftarbacaan.setJudul(url);
                                    }
                                });

                                StaticFunction.showSnackBar(parentView, "Berhasil ditambahkan ke daftar bacaan");
                            }
                        })
                        .setNegativeButton("Batal", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });

                alertDialog.show();
                break;
                */
        }
    }
}
