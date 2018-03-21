package com.example.samuelgespass.wordup.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.DefinitionArrayAdapter;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.services.WordnikService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DefinitionActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.wordText)
    TextView wordText;

    @BindView(R.id.definitionList)
    ListView listView;

    @BindView(R.id.buttonFavorite)
    Button button;

    ArrayList<Definition> definitions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        ButterKnife.bind(this);

        Intent wordIntent = getIntent();
        String word = wordIntent.getStringExtra("word");
        getDefinitions(word.trim().replaceAll("[^A-Za-z0-9 ]", ""));
        button.setOnClickListener(this);
    }

    private void getDefinitions(final String word) {
        final WordnikService wordnikService = new WordnikService();
        wordnikService.findWord(word, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                definitions = wordnikService.processDefinitionResults(response);
                DefinitionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wordText.setText(word);
                        DefinitionArrayAdapter definitionArrayAdapter = new DefinitionArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, definitions.toArray(new Definition[definitions.size()]));
                        listView.setAdapter(definitionArrayAdapter);

                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == button) {
            Intent wordIntent = getIntent();
            String word = wordIntent.getStringExtra("word");
            Intent newIntent = new Intent(DefinitionActivity.this, FavoritesActivity.class);
            newIntent.putExtra("word", word);
            startActivity(newIntent);
        }
    }
}
