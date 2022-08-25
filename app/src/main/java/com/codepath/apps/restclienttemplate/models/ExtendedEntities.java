package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
@Entity
public class ExtendedEntities {




    @ColumnInfo
    public String videoUrl;

    @ColumnInfo
    public String type2;

    public ExtendedEntities() {}

    public static ExtendedEntities fromJson(JSONObject jsonObject) throws JSONException {
        ExtendedEntities exEntities = new ExtendedEntities();
        // Check if a cover media is available
        if (!jsonObject.has("media")) {
            exEntities.videoUrl = "";
            exEntities.type2 = "";
        } else if(jsonObject.has("media")) {
            final JSONArray media_array = jsonObject.getJSONArray("media");
            exEntities.videoUrl = media_array.getJSONObject(0).getString("url");
            exEntities.type2 = media_array.getJSONObject(0).getString("type");

//            final JSONObject video_info = media_array.getJSONObject(0).getJSONObject("video_info");
//            final JSONArray variants_array = video_info.getJSONArray("variants");
//            exEntities.videoUrl = media_array.getJSONObject(0).getString("url");
//            exEntities.type = media_array.getJSONObject(0).getString("type");
        }
        return exEntities;
    }
}
