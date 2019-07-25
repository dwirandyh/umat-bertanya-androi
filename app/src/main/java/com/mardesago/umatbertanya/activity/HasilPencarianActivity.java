package com.mardesago.umatbertanya.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.adapter.ArtikelAdapter;
import com.mardesago.umatbertanya.adapter.ReferensiAdapter;
import com.mardesago.umatbertanya.adapter.TerpopulerAdapter;
import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.cari;
import com.mardesago.umatbertanya.model.referensi;
import com.mardesago.umatbertanya.service.ArtikelService;
import com.mardesago.umatbertanya.utils.Injector;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HasilPencarianActivity extends AppCompatActivity {

    @BindView(R.id.recylcer_artikel)
    RecyclerView recyclerArtikel;

    @BindView(R.id.parent_view)
    ConstraintLayout parentView;

    private String keyword;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_pencarian);
        ButterKnife.bind(this);

        keyword = getIntent().getStringExtra("keyword");

        getSupportActionBar().setTitle("Pencarian: " + keyword);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        progressDoalog = new ProgressDialog(this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Silahkan tunggu....");
        progressDoalog.show();
        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        ArtikelService service = Injector.artikelService();
        service.search(keyword).enqueue(new Callback<cari>() {
            @Override
            public void onResponse(Call<cari> call, Response<cari> response) {
                if (response.body() != null){
                    if (response.body().getArtikel().size() <= 0){
                        StaticFunction.showSnackBar(parentView, "Tidak ada data yang ditemukan");
                    }else{
                        initArtikel(response.body().getArtikel());
                    }
                }else{
                    StaticFunction.showSnackBar(parentView, "Tidak ada data yang ditemukan");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDoalog.dismiss();
                    }
                }, 1500);
            }

            @Override
            public void onFailure(Call<cari> call, Throwable t) {
                StaticFunction.showSnackBar(parentView, "Gagal mengambil data ke server", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        init();
                    }
                });
                progressDoalog.dismiss();
            }
        });
    }

    private void initArtikel(List<artikel> data){
        ArtikelAdapter adapter = new ArtikelAdapter(data, recyclerArtikel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerArtikel.setNestedScrollingEnabled(true);
        recyclerArtikel.setLayoutManager(layoutManager);
        recyclerArtikel.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerArtikel.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerArtikel.addItemDecoration(dividerItemDecoration);

        recyclerArtikel.setAdapter(adapter);
    }
}
