package com.mardesago.umatbertanya.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.adapter.DaftarBacaanAdapter;
import com.mardesago.umatbertanya.model.daftarbacaan;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class DaftarBacaanActivity extends AppCompatActivity {

    @BindView(R.id.recycler_daftar_bacaan)
    RecyclerView recyclerDaftarBacaan;

    @BindView(R.id.parent_view)
    ConstraintLayout parentView;

    private Realm realm;
    private DaftarBacaanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_bacaan);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Daftar Bacaan");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        realm = Realm.getDefaultInstance();

        initDaftarBacaan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_daftar_bacaan, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filterResult(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    adapter.filterResult("");
                }
                return true;
            }
        });

        return true;
    }

    private void initDaftarBacaan() {
        RealmResults<daftarbacaan> result = realm.where(daftarbacaan.class).findAll();
        adapter = new DaftarBacaanAdapter(result, true, parentView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerDaftarBacaan.setLayoutManager(layoutManager);
        recyclerDaftarBacaan.setItemAnimator(new DefaultItemAnimator());
        recyclerDaftarBacaan.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
