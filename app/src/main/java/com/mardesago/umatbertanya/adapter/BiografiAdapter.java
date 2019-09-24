package com.mardesago.umatbertanya.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.activity.BiografiActivity;
import com.mardesago.umatbertanya.model.imam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dwi Randy Herdinanto on 11/3/2017.
 */

public class BiografiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private List<imam> items;
    public RecyclerView recyclerView;
    public Context context;

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.txt_nama)
        TextView nama;
        @BindView(R.id.txt_keterangan)
        TextView keterangan;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            imam item = items.get(getAdapterPosition());
            Intent intent = new Intent(context, BiografiActivity.class);
            intent.putExtra("imam", item);
            context.startActivity(intent);
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        public HeaderHolder(View itemView) {
            super(itemView);

        }
    }


    public BiografiAdapter(List<imam> items, RecyclerView recyclerView) {
        this.items = items;
        this.recyclerView = recyclerView;
        this.context = recyclerView.getContext();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_biografi, parent, false);
        return new BiografiAdapter.ItemHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BiografiAdapter.ItemHolder) {
            BiografiAdapter.ItemHolder itemHolder = (BiografiAdapter.ItemHolder) holder;
            imam item = this.items.get(position);
            itemHolder.nama.setText(item.getNama());
            itemHolder.keterangan.setText(Html.fromHtml(item.getKeterangan()).subSequence(0, 100), TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
