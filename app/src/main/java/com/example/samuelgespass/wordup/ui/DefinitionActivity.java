package com.example.samuelgespass.wordup.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.DefinitionArrayAdapter;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.services.GiphyService;
import com.example.samuelgespass.wordup.services.WordnikService;
import com.squareup.picasso.Picasso;

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
    Button buttonFavorite;

    @BindView(R.id.googleButton)
    Button googleButton;

    @BindView(R.id.etymologyButton)
    Button etymologyButton;

    @BindView(R.id.wikipediaButton)
    Button wikipediaButton;

    @BindView(R.id.image)
    ImageView imageView;

    ArrayList<Definition> definitions = new ArrayList<>();

    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);
        ButterKnife.bind(this);

        Intent wordIntent = getIntent();
        String word = wordIntent.getStringExtra("word");
        getDefinitions(word.trim().replaceAll("[^A-Za-z0-9 ]", ""));
        buttonFavorite.setOnClickListener(this);
        googleButton.setOnClickListener(this);
        etymologyButton.setOnClickListener(this);
        wikipediaButton.setOnClickListener(this);
    }

    private void getDefinitions(final String word) {
        final GiphyService giphyService = new GiphyService();
        giphyService.findImage(word, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                imageUrl = giphyService.processImageUrl(response);
                Log.d("URL", imageUrl);
                DefinitionActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(getApplicationContext())
                                .load(imageUrl)
                                .noFade().into(imageView);
                    }
                });
            }
        });

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
        if (view == buttonFavorite) {
            Intent wordIntent = getIntent();
            String word = wordIntent.getStringExtra("word");
            Intent newIntent = new Intent(DefinitionActivity.this, FavoritesActivity.class);
            newIntent.putExtra("word", word);
            startActivity(newIntent);
        }

        if (view == googleButton) {
            Intent wordIntent = getIntent();
            String word = wordIntent.getStringExtra("word");
            String url = "https://www.google.com/search?q=" + word;
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(newIntent);
        }

        if (view == etymologyButton) {
            Intent wordIntent = getIntent();
            String word = wordIntent.getStringExtra("word");
            Intent newIntent = new Intent(DefinitionActivity.this, EtymologyActivity.class);
            newIntent.putExtra("word", word);
            startActivity(newIntent);
        }
        if (view == wikipediaButton) {
            Intent wordIntent = getIntent();
            String word = wordIntent.getStringExtra("word");
            String url = "https://en.wikipedia.org/wiki/" + word;
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(newIntent);
        }
    }
}
