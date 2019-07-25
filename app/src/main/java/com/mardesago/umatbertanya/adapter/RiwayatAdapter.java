package com.mardesago.umatbertanya.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.activity.DetailArtikelActivity;
import com.mardesago.umatbertanya.activity.DetailMazhabActivity;
import com.mardesago.umatbertanya.model.daftarbacaan;
import com.mardesago.umatbertanya.model.riwayat;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Case;
import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class RiwayatAdapter extends RealmRecyclerViewAdapter<riwayat, RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    OrderedRealmCollection<riwayat> items;
    public RecyclerView recyclerView;
    private final View parentView;
    Realm realm;

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_tgl)
        TextView tgl;
        @BindView(R.id.txt_keterangan)
        TextView keterangan;

        private View view;

        public ItemHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            riwayat riwayatItem = items.get(getAdapterPosition());

            if (riwayatItem.getId_artikel() != null){
                Intent intent = new Intent(itemView.getContext(), DetailArtikelActivity.class);
                intent.putExtra("id", riwayatItem.getId_artikel());
                itemView.getContext().startActivity(intent);
            }else {
                Intent intent = new Intent(itemView.getContext(), DetailMazhabActivity.class);
                intent.putExtra("id", riwayatItem.getId_mazhab());
                itemView.getContext().startActivity(intent);
            }
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }



    public RiwayatAdapter(@Nullable OrderedRealmCollection<riwayat> data, boolean autoUpdate, View view) {
        super(data, autoUpdate);
        this.items = data;
        realm = Realm.getDefaultInstance();
        this.parentView = view;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_riwayat, parent, false);
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
            riwayat item = this.items.get(position);
            itemHolder.tgl.setText(item.getTgl());
            itemHolder.keterangan.setText(item.getKeterangan());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void filterResult(String text){
        text = text == null ? null : text.toLowerCase().trim();
        RealmQuery<riwayat> query = realm.where(riwayat.class);
        if (!(text == null || "".equals(text))){
            query.contains("keterangan", text, Case.INSENSITIVE);
        }
        items = query.findAll();
        updateData(items);
        if (items.size() <= 0){
            StaticFunction.showSnackBar(parentView, "Data tidak ditemukan");
        }
    }
}
