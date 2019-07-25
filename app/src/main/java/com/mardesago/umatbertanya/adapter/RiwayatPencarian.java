package com.mardesago.umatbertanya.adapter;

import android.content.Intent;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.activity.DetailArtikelActivity;
import com.mardesago.umatbertanya.activity.HasilPencarianActivity;
import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.riwayatpencarian;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

public class RiwayatPencarian extends RealmRecyclerViewAdapter<riwayatpencarian, RecyclerView.ViewHolder> {
    private static final int ITEM = 0;
    private List<riwayatpencarian> items = new ArrayList<>();

    public RiwayatPencarian(@Nullable OrderedRealmCollection<riwayatpencarian> data, boolean autoUpdate) {
        super(data, autoUpdate);
        this.items = data;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_judul)
        TextView judul;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HasilPencarianActivity.class);
            riwayatpencarian item = items.get(getAdapterPosition());
            intent.putExtra("keyword", item.getKeyword());
            view.getContext().startActivity(intent);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_terpopuler, parent, false);
        return new RiwayatPencarian.ItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RiwayatPencarian.ItemHolder) {
            RiwayatPencarian.ItemHolder itemHolder = (RiwayatPencarian.ItemHolder) holder;
            riwayatpencarian item = this.items.get(position);
            String judul = item.getKeyword();
            itemHolder.judul.setText(judul);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}