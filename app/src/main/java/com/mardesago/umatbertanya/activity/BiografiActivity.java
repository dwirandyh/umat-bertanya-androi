package com.mardesago.umatbertanya.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.model.imam;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BiografiActivity extends AppCompatActivity {

    @BindView(R.id.txt_nama)
    TextView txtNama;
    @BindView(R.id.txt_keterangan)
    WebView txtKeterangan;
    @BindView(R.id.foto)
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biografi);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        imam data = (imam) intent.getSerializableExtra("imam");

        getSupportActionBar().setTitle(data.getNama());
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        txtNama.setText(data.getNama());
        //txtKeterangan.setText(data.getKeterangan());
        txtKeterangan.getSettings().setJavaScriptEnabled(true);
        txtKeterangan.setBackgroundColor(Color.TRANSPARENT);
        if (data.getKeterangan() != null){
            txtKeterangan.loadData(data.getKeterangan(), "text/html", "UTF-8");
            //txtKeterangan.setWebViewClient(webViewClient);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
