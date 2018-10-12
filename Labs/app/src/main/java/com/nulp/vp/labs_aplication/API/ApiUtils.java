package com.nulp.vp.labs_aplication.API;

/**
 * Created by Vova0199 on 24.09.2018.
 */
public class ApiUtils {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }
}