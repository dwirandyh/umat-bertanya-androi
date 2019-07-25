package com.mardesago.umatbertanya.service;

import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.referensi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Febrian Reza on 21-Nov-17.
 */

public interface ReferensiService {
    @GET( "artikel/referensi" )
    Call<List<referensi>> getReferensi(@Query("id") String id);
}
