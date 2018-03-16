package com.example.samuelgespass.wordup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefinitionActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.wordText)
    TextView wordText;

    @BindView(R.id.definitionList)
    ListView listView;

    @BindView(R.id.buttonFavorite)
    Button button;

    String[] dictionaries = {
            "ahd-legacy", "wiktionary", "webster", "wordnet", "wordnet"
    };

    String[] definitions = {
            "noun, lorem ipsum sin amat de amus triangle cat", "verb, to bear with a bear with a beer", "adjective, a strange fruity colour", "adverb, quickly", "noun, a unit of measurement larger than a cubit but smaller than a mile"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        ButterKnife.bind(this);

        Intent wordIntent = getIntent();
        String word = wordIntent.getStringExtra("word");
        wordText.setText(word);
        DefinitionArrayAdapter definitionArrayAdapter = new DefinitionArrayAdapter(this, android.R.layout.simple_list_item_1, dictionaries, definitions);
        listView.setAdapter(definitionArrayAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view == button) {
            Intent wordIntent = getIntent();
            String word = wordIntent.getStringExtra("word");
            Intent newIntent = new Intent(DefinitionActivity.this, FavoritesActivity.class);
            startActivity(newIntent);
        }
    }
}
