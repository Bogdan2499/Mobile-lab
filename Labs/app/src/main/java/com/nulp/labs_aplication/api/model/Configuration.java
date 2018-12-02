package com.nulp.labs_aplication.api.model;

/**
 * Created by Vova0199 on 18/11/2018.
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Configuration {

    @JsonProperty("images")
    public Images images;
    @JsonProperty("change_keys")
    public List<String> changeKeys = null;

}
