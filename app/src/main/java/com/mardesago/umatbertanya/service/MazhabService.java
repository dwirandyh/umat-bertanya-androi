package com.mardesago.umatbertanya.service;

import com.mardesago.umatbertanya.model.artikel;
import com.mardesago.umatbertanya.model.cari;
import com.mardesago.umatbertanya.model.mazhab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Febrian Reza on 21-Nov-17.
 */

public interface MazhabService {

    @GET( "mazhab" )
    Call<List<mazhab>> getMazhab(@Query("imam") String imam);

    @GET("mazhab/detail")
    Call<mazhab> getDetail(@Query("id") String id);

}
