package com.nulp.vp.labs_aplication.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Vova0199 on 24.09.2018.
 */
public class ListFilms {
    @SerializedName("results")
    private List<Film> results = null;

    public List<Film> getFilms() {
        return results;
    }
}