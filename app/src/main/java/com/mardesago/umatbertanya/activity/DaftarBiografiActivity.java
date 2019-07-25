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

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.adapter.BiografiAdapter;
import com.mardesago.umatbertanya.model.imam;
import com.mardesago.umatbertanya.service.ImamService;
import com.mardesago.umatbertanya.utils.Injector;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarBiografiActivity extends AppCompatActivity {

    @BindView(R.id.recycler_biografi)
    RecyclerView recyclerBiografi;

    @BindView(R.id.parent_view)
    ConstraintLayout parentView;

    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_biografi);
        ButterKnife.bind(this);


        getSupportActionBar().setTitle("Biografi Mazhab");
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
        ImamService service = Injector.imamService();
        service.getImam().enqueue(new Callback<List<imam>>() {
            @Override
            public void onResponse(Call<List<imam>> call, Response<List<imam>> response) {
                if (response.body() != null){
                    if (response.body().size() <= 0){
                        StaticFunction.showSnackBar(parentView, "Tidak ada data yang ditemukan");
                    }else{
                        initBiografi(response.body());
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
            public void onFailure(Call<List<imam>> call, Throwable t) {
                init();
            }
        });
    }

    private void initBiografi(List<imam> data){
        BiografiAdapter adapter = new BiografiAdapter(data, recyclerBiografi);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerBiografi.setNestedScrollingEnabled(true);
        recyclerBiografi.setLayoutManager(layoutManager);
        recyclerBiografi.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerBiografi.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerBiografi.addItemDecoration(dividerItemDecoration);

        recyclerBiografi.setAdapter(adapter);
    }
}
