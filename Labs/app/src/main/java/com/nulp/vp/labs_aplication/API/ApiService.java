package com.nulp.vp.labs_aplication.API;

import com.nulp.vp.labs_aplication.Model.ListFilms;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Vova0199 on 24.09.2018.
 */
public interface ApiService {

    @GET("trending/movie/week?api_key=7ae5035398e0c1d3b0b4219a64866f37")
    Call<ListFilms> getAnswers();

}
