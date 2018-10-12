package com.nulp.vp.labs_aplication.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Vova0199 on 24.09.2018.
 */
public class Film {
    public Film(String title, String overview, String posterPath, String voteAverage) {
        this.overview = overview;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
    }


    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vote_average")
    @Expose
    private String voteAverage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return String.valueOf(voteAverage);
    }

}