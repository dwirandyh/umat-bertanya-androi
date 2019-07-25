package com.mardesago.umatbertanya.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.activity.DetailArtikelActivity;
import com.mardesago.umatbertanya.model.artikel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class ArtikelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private List<artikel> items;
    public RecyclerView recyclerView;
    public Context context;

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_judul)
        TextView judul;
        @BindView(R.id.txt_deskripsi)
        TextView deskripsi;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(recyclerView.getContext(), DetailArtikelActivity.class);
            artikel item = items.get(getAdapterPosition());
            intent.putExtra("id", item.getId_artikel());
            context.startActivity(intent);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }


    public ArtikelAdapter(List<artikel> items, RecyclerView recyclerView) {
        this.items = items;
        this.recyclerView = recyclerView;
        this.context = recyclerView.getContext();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_artikel, parent, false);
        return new ArtikelAdapter.ItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ArtikelAdapter.ItemHolder) {
            ArtikelAdapter.ItemHolder itemHolder = (ArtikelAdapter.ItemHolder) holder;
            artikel item = this.items.get(position);
            itemHolder.judul.setText(item.getJudul());
            if (item.getDeskripsi() != null){
                itemHolder.deskripsi.setText(Html.fromHtml(item.getDeskripsi()).subSequence(0, 200), TextView.BufferType.SPANNABLE);
            }else{
                itemHolder.deskripsi.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
