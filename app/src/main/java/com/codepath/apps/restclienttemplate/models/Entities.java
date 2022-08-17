package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

@Parcel
public class Entities {

    public String media_url, type;

    public Entities() {}

    public static Entities fromJson(JSONObject jsonObject) throws JSONException {
        Entities entities = new Entities();
        // Check if a cover media is available
        if (!jsonObject.has("media")) {
            entities.media_url = "";
            entities.type = "";
        } else if(jsonObject.has("media")) {
            final JSONArray media_array = jsonObject.getJSONArray("media");
            entities.media_url = media_array.getJSONObject(0).getString("media_url_https");
            entities.type = media_array.getJSONObject(0).getString("type");
        }
        return entities;
    }

}
