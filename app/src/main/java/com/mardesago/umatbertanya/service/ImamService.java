package com.mardesago.umatbertanya.service;

import com.mardesago.umatbertanya.model.imam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Febrian Reza on 21-Nov-17.
 */

public interface ImamService {
    @GET( "imam" )
    Call<List<imam>> getImam();
}
