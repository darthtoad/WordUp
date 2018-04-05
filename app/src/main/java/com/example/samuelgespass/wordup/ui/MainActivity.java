package com.example.samuelgespass.wordup.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.titleText) TextView titleText;

    @BindView(R.id.searchBar) EditText searchBar;

    @BindView(R.id.searchButton) Button searchButton;

    @BindView(R.id.favoritesButton) Button favoritesButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    String dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typeface tusj = Typeface.createFromAsset(getAssets(), "fonts/FFF_Tusj.ttf");
        titleText.setTypeface(tusj);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        searchButton.setOnClickListener(this);
        favoritesButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_ahd:
                if (checked) {
                    dictionary = "ahd";
                }
                break;
            case R.id.radio_webster:
                if (checked) {
                    dictionary = "webster";
                }
                break;
            case R.id.radio_wordnet:
                if (checked) {
                    dictionary = "wordnet";
                }
                break;
            default:
                dictionary = "wiktionary";
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == searchButton) {
            String word = searchBar.getText().toString();
            if (!word.equals("")) {
                Intent intent = new Intent(MainActivity.this, DefinitionActivity.class);
                intent.putExtra("word", word);
                if (dictionary == null) {
                    dictionary = "wiktionary";
                }
                intent.putExtra("dictionary", dictionary);
                addToSharedPreferences(word);
                startActivity(intent);
            } else if (!sharedPreferences.getString(Constants.PREFERENCES_WORD_KEY, null).equals(null)) {
                Intent intent = new Intent(MainActivity.this, DefinitionActivity.class);
                word = sharedPreferences.getString(Constants.PREFERENCES_WORD_KEY, null);
                intent.putExtra("word", word);
                intent.putExtra("dictionary", dictionary);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Please enter a word", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        if (v == favoritesButton) {
            Intent intent = new Intent(MainActivity.this, SavedDefinitionListActivity.class);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String word) {
        editor.putString(Constants.PREFERENCES_WORD_KEY, word).apply();
    }
}
