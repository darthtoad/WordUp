package com.example.samuelgespass.wordup.services;

import android.util.Log;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.models.Definition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 3/21/18.
 */

public class WordnikService {
    public static void findWord(String word, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
//        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.WORDNIK_BASE_URL).newBuilder();
//        urlBuilder.addQueryParameter(Constants.WORDNIK_WORD_QUERY_PARAMETER, word);
//        urlBuilder.addPathSegment(Constants.WORDNIK_DEFINITION);
//        urlBuilder.addPathSegment(Constants.WORDNIK_TOKEN);
        String url = "http://api.wordnik.com:80/v4/word.json/" + word + "/definitions?api_key=a2a73e7b926c924fad7001ca3111acd55af2ffabf50eb4ae5";
        Log.d("URL", url);

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Definition> processDefinitionResults(Response response) {
        ArrayList<Definition> definitions = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            Log.d("", "processDefinitionResults: " + jsonData);
            JSONArray wordnikJSON = new JSONArray(jsonData);
            for (int i = 0; i < wordnikJSON.length(); i++) {
                JSONObject definitionJSON = wordnikJSON.getJSONObject(i);
                String word = definitionJSON.getString("word");
                String partOfSpeech = definitionJSON.getString("partOfSpeech");
                String attributionText = definitionJSON.getString("attributionText");
                String definitionText = definitionJSON.getString("text");

                Definition definition = new Definition(word, partOfSpeech, attributionText, definitionText);
                definitions.add(definition);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return definitions;
    }
}