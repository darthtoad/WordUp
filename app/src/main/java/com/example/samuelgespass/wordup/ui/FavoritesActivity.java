package com.example.samuelgespass.wordup.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.FavoritesAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private FavoritesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] oldWords = {
                "Frankfurt", "thing", "Platypus", "chameleon", "comraderie"
        };

        ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(oldWords));


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        if (!word.equals("")) {
            wordsList.add(word);
        }

        adapter = new FavoritesAdapter(getApplicationContext(), wordsList);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FavoritesActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }
}
