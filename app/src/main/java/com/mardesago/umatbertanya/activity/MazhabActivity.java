package com.mardesago.umatbertanya.activity;

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
import com.mardesago.umatbertanya.adapter.MazhabAdapter;
import com.mardesago.umatbertanya.model.mazhab;
import com.mardesago.umatbertanya.service.MazhabService;
import com.mardesago.umatbertanya.utils.Injector;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MazhabActivity extends AppCompatActivity {

    @BindView(R.id.recycler_kategori)
    RecyclerView recyclerView;
    @BindView(R.id.parent_view)
    ConstraintLayout parentView;

    String imam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mazhab);

        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getIntent().getStringExtra("imam"));
        imam = getIntent().getStringExtra("imam");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

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


    private void init() {
        MazhabService service = Injector.mazhabService();
        service.getMazhab(imam).enqueue(new Callback<List<mazhab>>() {
            @Override
            public void onResponse(Call<List<mazhab>> call, Response<List<mazhab>> response) {
                if (response.body() != null) {
                    if (response.body().size() <= 0) {
                        StaticFunction.showSnackBar(parentView, "Tidak ada data yang ditemukan");
                    } else {
                        initMazhab(response.body());
                    }
                } else {
                    StaticFunction.showSnackBar(parentView, "Tidak ada data yang ditemukan");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //progressDoalog.dismiss();
                    }
                }, 1500);
            }

            @Override
            public void onFailure(Call<List<mazhab>> call, Throwable t) {
                StaticFunction.showSnackBar(parentView, "Gagal mengambil data ke server", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        init();
                    }
                });
            }

        });
    }

    private void initMazhab(List<mazhab> data) {
        /*
        MazhabAdapter adapter = new MazhabAdapter(data, recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapter);
        */
    }
}
