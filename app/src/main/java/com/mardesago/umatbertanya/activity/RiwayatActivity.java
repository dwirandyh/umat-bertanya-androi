package com.mardesago.umatbertanya.activity;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.adapter.DaftarBacaanAdapter;
import com.mardesago.umatbertanya.adapter.RiwayatAdapter;
import com.mardesago.umatbertanya.model.daftarbacaan;
import com.mardesago.umatbertanya.model.riwayat;
import com.mardesago.umatbertanya.model.riwayat;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RiwayatActivity extends AppCompatActivity {

    @BindView(R.id.recycler_riwayat)
    RecyclerView recyclerRiwayat;

    @BindView(R.id.parent_view)
    ConstraintLayout parentView;
    private Realm realm;
    private RiwayatAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        ButterKnife.bind(this);


        getSupportActionBar().setTitle("Riwayat");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        realm = Realm.getDefaultInstance();

        initRiwayat();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }else if (item.getItemId() == R.id.action_hapus){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.where(riwayat.class).findAll().deleteAllFromRealm();
                    StaticFunction.showSnackBar(parentView, "Riwayat berhasil dibersihkan");
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.activity_riwayat, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
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

    private void initRiwayat() {
        RealmResults<riwayat> result = realm.where(riwayat.class).findAllSorted("id_riwayat", Sort.DESCENDING);
        adapter = new RiwayatAdapter(result, true, parentView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerRiwayat.setLayoutManager(layoutManager);
        recyclerRiwayat.setItemAnimator(new DefaultItemAnimator());
        recyclerRiwayat.setAdapter(adapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
