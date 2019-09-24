package com.mardesago.umatbertanya.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.adapter.RiwayatPencarian;
import com.mardesago.umatbertanya.dialog.SplashDialog;
import com.mardesago.umatbertanya.model.mazhab;
import com.mardesago.umatbertanya.model.riwayatpencarian;
import com.mardesago.umatbertanya.model.tag;
import com.mardesago.umatbertanya.service.ArtikelService;
import com.mardesago.umatbertanya.service.MazhabService;
import com.mardesago.umatbertanya.utils.Injector;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @BindView(R.id.txt_cari)
    AutoCompleteTextView txtCari;
    @BindView(R.id.btn_cari)
    ImageButton btnCari;
    private Context context;
    @BindView(R.id.recycler_riwayat_pencarian)
    RecyclerView recyclerRiwayatPencarian;

    @BindView(R.id.parent_view)
    CoordinatorLayout parentView;
    private RiwayatPencarian adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SplashDialog dialog = new SplashDialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.show();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = this;

        toolbar.setTitle("Umat Bertanya");
        setSupportActionBar(toolbar);
        // add back arrow to toolbar


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        initRiwayatPencarian();
        initTag();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void initTag() {
        ArtikelService service = Injector.artikelService();
        service.getTag().enqueue(new Callback<List<tag>>() {
            @Override
            public void onResponse(Call<List<tag>> call, Response<List<tag>> response) {
                if (response.body() != null) {
                    ArrayList<String> dataSource = new ArrayList<>();
                    for (int i = 0; i <= response.body().size() - 1; i++) {
                        dataSource.add(response.body().get(i).getTagNama().trim());
                    }

                    ArrayAdapter<String> adapter = new
                            ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, dataSource);

                    txtCari.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<tag>> call, Throwable t) {
                StaticFunction.showSnackBar(parentView, "Gagal mengambil tag dari server", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initTag();
                    }
                });
            }
        });
    }


    private void init() {
        MazhabService service = Injector.mazhabService();
        service.getMazhab("").enqueue(new Callback<List<mazhab>>() {
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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bacaan) {
            Intent intent = new Intent(MainActivity.this, DaftarBacaanActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mazhab) {
            Intent intent = new Intent(MainActivity.this, DaftarBiografiActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_riwayat) {
            Intent intent = new Intent(MainActivity.this, RiwayatActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bantuan) {
            Intent intent = new Intent(MainActivity.this, TentangActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.btn_cari)
    public void btnCariOnClick() {
        String keyword = txtCari.getText().toString();
        if (keyword.equals("")) {
            StaticFunction.showSnackBar(drawer, "Kata kunci pencarian harus diisi");
            return;
        }

        this.simpanRiwayatPencarian(keyword);

        Intent intent = new Intent(context, HasilPencarianActivity.class);
        intent.putExtra("keyword", txtCari.getText().toString());
        startActivity(intent);
    }

    private void simpanRiwayatPencarian(final String keyword) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentId = realm.where(riwayatpencarian.class).max("id_riwayat");
                int nextId = (currentId == null) ? 1 : currentId.intValue() + 1;
                riwayatpencarian obj = realm.createObject(riwayatpencarian.class, nextId);
                obj.setKeyword(keyword);

            }
        });
    }


    private void initRiwayatPencarian() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<riwayatpencarian> result = realm.where(riwayatpencarian.class).findAllSorted("id_riwayat", Sort.DESCENDING).distinct("keyword");
        adapter = new RiwayatPencarian(result, true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerRiwayatPencarian.setLayoutManager(layoutManager);
        recyclerRiwayatPencarian.setItemAnimator(new DefaultItemAnimator());
        recyclerRiwayatPencarian.setAdapter(adapter);
    }

}
