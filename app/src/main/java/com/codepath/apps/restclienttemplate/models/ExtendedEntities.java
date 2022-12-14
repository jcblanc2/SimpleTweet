package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class ExtendedEntities {

    @ColumnInfo
    @PrimaryKey
    public Long idExEntities;

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
            exEntities.idExEntities = media_array.getJSONObject(0).getLong("id");
            exEntities.type2 = media_array.getJSONObject(0).getString("type");

            if(exEntities.type2.equals("video")){
                JSONObject video_info = media_array.getJSONObject(0).getJSONObject("video_info");
//                JSONArray variants = video_info.getJSONArray("variants");
            }else {
                exEntities.videoUrl = "";
            }
        }
        return exEntities;
    }


}
