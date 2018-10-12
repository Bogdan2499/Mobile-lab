package com.nulp.vp.labs_aplication.Model;

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

    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("title")
    private String title;
    @SerializedName("vote_average")
    private String voteAverage;

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAverage() {
        return String.valueOf(voteAverage);
    }
}