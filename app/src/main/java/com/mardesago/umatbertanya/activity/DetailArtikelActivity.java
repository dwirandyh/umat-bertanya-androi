package com.mardesago.umatbertanya.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mardesago.umatbertanya.R;
import com.mardesago.umatbertanya.adapter.MazhabAdapter;
import com.mardesago.umatbertanya.dialog.AddBookmarkDialog;
import com.mardesago.umatbertanya.dialog.BiografiDialog;
import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.imam;
import com.mardesago.umatbertanya.model.mazhab;
import com.mardesago.umatbertanya.model.referensi;
import com.mardesago.umatbertanya.model.riwayat;
import com.mardesago.umatbertanya.service.ArtikelService;
import com.mardesago.umatbertanya.service.ImamService;
import com.mardesago.umatbertanya.utils.Injector;
import com.mardesago.umatbertanya.utils.StaticFunction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailArtikelActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.parent_view)
    ConstraintLayout parentView;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;


    @BindView(R.id.webview_deskripsi)
    WebView webViewDeskripsi;

    @BindView(R.id.recycler_mazhab)
    RecyclerView recyclerMazhab;

    private List<imam> imamList;

    artikel data;
    private Context context;
    private List<referensi> referensiList;
    private AlertDialog.Builder alertDialog;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);
        ButterKnife.bind(this);

        this.context = this;

        getSupportActionBar().setTitle("Rincian Bacaan");
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

    private void init() {
        getData();
        getImam();
        //initReferensi();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_bookmark:
                        bookmark();
                        break;
                    case R.id.action_bagikan:
                        bagikan();
                        break;
                    case R.id.action_cari:
                        cari();
                        break;
                }

                return true;
            }
        });
    }


    private void bagikan() {
        String message = data.getPlain_deskripsi();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/html");
        share.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(share, "Bagikan"));
    }

    private void bookmark() {
        AddBookmarkDialog dialog = new AddBookmarkDialog(this, R.style.CustomBottomSheetDialog, data, parentView);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    private void cari(){
        final EditText input = new EditText(context);
        input.setSingleLine();
        final FrameLayout container = new FrameLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
        params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
        input.setLayoutParams(params);
        container.addView(input);

        alertDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogMaterialStyle)
                .setTitle("Kata kunci Pencarian")
                .setView(container)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        final String keyword = input.getText().toString();
                        if (keyword.equals("")){
                            webViewDeskripsi.loadData(data.getDeskripsi(), "text/html", "UTF-8");
                        }else{
                            String deskripsi = data.getDeskripsi().toLowerCase().replace(keyword.toLowerCase(), "<span style=\"background-color: #fa8729\">" + keyword + "</span>");
                            webViewDeskripsi.loadData(deskripsi, "text/html", "UTF-8");
                        }

                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void initDeskripsi() {
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    url = request.getUrl().toString();
                }
                if (url != null && url.toLowerCase().contains("immid")) {
                    String[] imams = url.split("%3D");
                    imams[1] = imams[1].replace("/", "");
                    for (com.mardesago.umatbertanya.model.imam imam : imamList) {
                        if (imam.getId_imam() == Integer.valueOf(imams[1])) {
                            showBiografiDialog(imam);
                            return true;
                        }
                    }
                }
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.toLowerCase().contains("immid")) {
                    String[] imams = url.split("%3D");
                    imams[1] = imams[1].replace("/", "");
                    for (com.mardesago.umatbertanya.model.imam imam : imamList) {
                        if (imam.getId_imam() == Integer.valueOf(imams[1])) {
                            showBiografiDialog(imam);
                            return true;
                        }
                    }
                }
                return true;
                //return super.shouldOverrideUrlLoading(view, url);
            }
        };
        webViewDeskripsi.getSettings().setJavaScriptEnabled(true);
        webViewDeskripsi.setBackgroundColor(Color.TRANSPARENT);
        if (data.getDeskripsi() != null){
            String deskripsi = data.getDeskripsi().replace("a href=\"", "a href=\"http://");
            data.setDeskripsi(deskripsi);
            webViewDeskripsi.loadData(data.getDeskripsi(), "text/html", "UTF-8");
            webViewDeskripsi.setWebViewClient(webViewClient);
        }
    }

    private void showBiografiDialog(imam data) {
        BiografiDialog dialog = new BiografiDialog(this, R.style.CustomBottomSheetDialog, data);
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    private void getData() {
        ArtikelService service = Injector.artikelService();
        String idArtikel = getIntent().getStringExtra("id");
        service.getDetail(idArtikel).enqueue(new Callback<artikel>() {
            @Override
            public void onResponse(Call<artikel> call, Response<artikel> response) {
                data = response.body();

                getSupportActionBar().setTitle(data.getJudul());
                initDeskripsi();

                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Number currentId = realm.where(riwayat.class).max("id_riwayat");
                        int nextId = (currentId == null) ? 1 : currentId.intValue() + 1;
                        riwayat obj = realm.createObject(riwayat.class, nextId);
                        obj.setId_artikel(data.getId_artikel());
                        String tgl = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss").format(new Date());
                        obj.setTgl(tgl);
                        obj.setKeterangan(data.getJudul());
                    }
                });


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailArtikelActivity.this);
                recyclerMazhab.setLayoutManager(linearLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerMazhab.getContext(),
                        linearLayoutManager.getOrientation());
                recyclerMazhab.addItemDecoration(dividerItemDecoration);

                List<mazhab> mazhabList = response.body().getMazhab();
                recyclerMazhab.setAdapter(new MazhabAdapter(recyclerMazhab, mazhabList));
                progressDoalog.dismiss();

            }

            @Override
            public void onFailure(Call<artikel> call, Throwable t) {
                StaticFunction.showSnackBar(parentView, "Gagal mengambil data bacaan", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                });
                progressDoalog.dismiss();
            }
        });
    }

    private void getImam() {
        ImamService service = Injector.imamService();
        service.getImam().enqueue(new Callback<List<imam>>() {
            @Override
            public void onResponse(Call<List<imam>> call, Response<List<imam>> response) {
                imamList = response.body();
            }

            @Override
            public void onFailure(Call<List<imam>> call, Throwable t) {
                StaticFunction.showSnackBar(parentView, "Gagal mengambil daftar imam", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                });
            }
        });
    }
}
