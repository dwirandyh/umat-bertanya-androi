package com.mardesago.umatbertanya.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.model.referensi;
import com.mardesago.umatbertanya.model.referensi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class ReferensiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private List<referensi> items;
    public RecyclerView recyclerView;

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.txt_deskripsi)
        TextView deskripsi;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }


    public ReferensiAdapter(List<referensi> items, RecyclerView recyclerView) {
        this.items = items;
        this.recyclerView = recyclerView;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_referensi, parent, false);
        return new ReferensiAdapter.ItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ReferensiAdapter.ItemHolder) {
            ReferensiAdapter.ItemHolder itemHolder = (ReferensiAdapter.ItemHolder) holder;
            referensi item = this.items.get(position);
            itemHolder.deskripsi.setText(item.getDeskripsi());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
