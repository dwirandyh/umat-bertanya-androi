package com.mardesago.umatbertanya.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.activity.DetailArtikelActivity;
import com.mardesago.umatbertanya.activity.DetailMazhabActivity;
import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.daftarbacaan;
import com.mardesago.umatbertanya.model.daftarbacaan;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class DaftarBacaanAdapter extends RealmRecyclerViewAdapter<daftarbacaan, RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private final View parentView;
    OrderedRealmCollection<daftarbacaan> items;
    Realm realm;


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_judul)
        TextView judul;
        @BindView(R.id.btn_aksi)
        ImageView btnAksi;

        private View view;

        public ItemHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @OnClick(R.id.btn_aksi)
        public void btnAksiOnClick() {
            PopupMenu popup = new PopupMenu(itemView.getContext(), btnAksi);
            //inflating menu from xml resource
            popup.inflate(R.menu.option_menu);
            //adding click listener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    final daftarbacaan data = items.get(getAdapterPosition());
                    switch (item.getItemId()) {
                        case R.id.action_ubah_nama:
                            final EditText input = new EditText(itemView.getContext());
                            input.setSingleLine();
                            final FrameLayout container = new FrameLayout(itemView.getContext());
                            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            params.leftMargin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.fab_margin);
                            params.rightMargin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.fab_margin);
                            input.setLayoutParams(params);
                            container.addView(input);

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext(), R.style.MyAlertDialogMaterialStyle)
                                    .setTitle("Ubah Judul Bacaan")
                                    .setView(container)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            final String judul = input.getText().toString();
                                            realm.executeTransaction(new Realm.Transaction() {
                                                @Override
                                                public void execute(Realm realm) {
                                                    daftarbacaan obj =  realm.where(daftarbacaan.class).equalTo("id_daftar", data.getId_daftar()).findFirst();
                                                    obj.setJudul(judul);
                                                }
                                            });

                                            StaticFunction.showSnackBar(parentView, "Judul Bacaan Berhasil Diubah");
                                        }
                                    })
                                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    });

                            alertDialog.show();
                            break;
                        case R.id.action_hapus:
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.where(daftarbacaan.class).equalTo("id_daftar", data.getId_daftar())
                                            .findFirst()
                                            .deleteFromRealm();
                                }
                            });
                            StaticFunction.showSnackBar(parentView, "Daftar bacaan berhasil dihapus");
                            break;
                    }
                    return false;
                }
            });
            //displaying the popup
            popup.show();
        }

        @Override
        public void onClick(View view) {
            daftarbacaan bacaanItem = items.get(getAdapterPosition());

            if (bacaanItem.getId_artikel() != null){
                Intent intent = new Intent(itemView.getContext(), DetailArtikelActivity.class);
                intent.putExtra("id", bacaanItem.getId_artikel());
                itemView.getContext().startActivity(intent);
            }else {
                Intent intent = new Intent(itemView.getContext(), DetailMazhabActivity.class);
                intent.putExtra("id", bacaanItem.getId_mazhab());
                itemView.getContext().startActivity(intent);
            }
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }


    //public DaftarBacaanAdapter(List<daftarbacaan> items, RecyclerView recyclerView) {
    //this.items = items;
    //this.recyclerView = recyclerView;
    //}


    public DaftarBacaanAdapter(@Nullable OrderedRealmCollection<daftarbacaan> data, boolean autoUpdate, View view) {
        super(data, autoUpdate);
        this.items = data;
        realm = Realm.getDefaultInstance();
        parentView = view;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_daftar_bacaan, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            ItemHolder itemHolder = (ItemHolder) holder;
            daftarbacaan item = this.items.get(position);
            itemHolder.judul.setText(item.getJudul());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filterResult(String text){
        text = text == null ? null : text.toLowerCase().trim();
        RealmQuery<daftarbacaan> query = realm.where(daftarbacaan.class);
        if (!(text == null || "".equals(text))){
            query.contains("judul", text, Case.INSENSITIVE);
        }
        items = query.findAll();
        updateData(items);

        if (items.size() <= 0){
            StaticFunction.showSnackBar(parentView, "Data tidak ditemukan");
        }
    }
}
