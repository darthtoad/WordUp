package com.example.samuelgespass.wordup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

public class FavoritesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] oldWords = {
                "Frankfurt", "ABBA", "Platypus", "Chameleon", "Comraderie"
        };

        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(oldWords));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        if (!word.equals("")) {
            wordsList.add(word);
        }
        String[] words = wordsList.toArray(new String[wordsList.size()]);
        words = wordsList.toArray(words);

        GridView gridView = (GridView) findViewById(R.id.baseGridView);
        gridView.setAdapter(new FavoritesAdapter(this, words));

    }
}
