package com.example.samuelgespass.wordup.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelgespass.wordup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.titleText) TextView titleText;

    @BindView(R.id.searchBar) EditText searchBar;

    @BindView(R.id.searchButton) Button searchButton;

    @BindView(R.id.favoritesButton) Button favoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface tusj = Typeface.createFromAsset(getAssets(), "fonts/FFF_Tusj.ttf");
        titleText.setTypeface(tusj);
        searchButton.setOnClickListener(this);
        favoritesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            String word = searchBar.getText().toString();
            if (!word.equals("")) {
                Intent intent = new Intent(MainActivity.this, DefinitionActivity.class);
                intent.putExtra("word", word);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Please enter a word", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (v == favoritesButton) {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            intent.putExtra("word", "");
            startActivity(intent);
        }
    }
}
