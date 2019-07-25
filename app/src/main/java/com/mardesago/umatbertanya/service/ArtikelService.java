package com.mardesago.umatbertanya.service;

import android.nfc.Tag;

import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.cari;
import com.mardesago.umatbertanya.model.tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Febrian Reza on 21-Nov-17.
 */

public interface ArtikelService {

    @GET( "artikel/populer" )
    Call<List<artikel>> getPopuler();

    @GET("artikel/detail")
    Call<artikel> getDetail(@Query("id") String id);

    @GET("artikel/cari")
    Call<cari> search(@Query("keyword") String keyword);

    @GET("tag")
    Call<List<tag>> getTag();

}
