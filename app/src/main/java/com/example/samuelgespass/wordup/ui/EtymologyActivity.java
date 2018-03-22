package com.example.samuelgespass.wordup.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.services.WordnikService;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EtymologyActivity extends AppCompatActivity {
    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.titleView)
    TextView titleView;

    String etymologies;
    String wordFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etymology);
        ButterKnife.bind(this);

        Intent wordIntent = getIntent();
        String wordFromIntent = wordIntent.getStringExtra("word");
        titleView.setText("Etymology of " + wordFromIntent);
        getEtymologies(wordFromIntent);
    }

    private void getEtymologies(final String word) {
        final WordnikService wordnikService = new WordnikService();
        wordnikService.findEtymology(word, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                etymologies = wordnikService.processEtymologyResults(response);
                Log.d("Etymologies", etymologies);
                EtymologyActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(etymologies);
                    }
                });
            }
        });
    }
}
