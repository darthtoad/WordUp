package com.example.samuelgespass.wordup.services;

import android.util.Log;

import com.example.samuelgespass.wordup.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 3/22/18.
 */

public class GiphyService {
    public static void findImage(String word, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        String url = Constants.GIPHY_BASE_URL + word + "&api_key=" + Constants.GIPHY_TOKEN;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public String processImageUrl(Response response) {
        String imageUrl = "";

        try {
            String jsonData = response.body().string();
            JSONObject giphyJSON = new JSONObject(jsonData);
            JSONArray data = giphyJSON.getJSONArray("data");
            JSONObject imageObject = data.getJSONObject(0);
            JSONObject fullImages = imageObject.getJSONObject("images");
            JSONObject smallImage = fullImages.getJSONObject("fixed_height_small");
            imageUrl = smallImage.getString("url");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return imageUrl;
    }
}
