
package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.ui.DefinitionActivity;
import com.example.samuelgespass.wordup.ui.DefinitionDetailFragment;
import com.example.samuelgespass.wordup.ui.DefinitionFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Guest on 3/29/18.
 */

public class FirebaseWordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View view;
    Context context;
    String word;
    ArrayList<String> words;
    Button clickText;
    TextView wordTextView;
    Definition definition = new Definition("", "", "", "", "");
    private ValueEventListener listener;
    DatabaseReference wordRef;
    public ImageView image;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Set<String> definitionSet = new HashSet<>();
    ArrayList<Definition> definitions = new ArrayList<>();
    private int orientation;


    public FirebaseWordViewHolder(View itemView) {
        super(itemView);
        view = itemView;
//        orientation = itemView.getResources().getConfiguration().orientation;
        context = itemView.getContext();
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        editor = sharedPreferences.edit();
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            createDefinitionFragment(0);
//        }
    }

    public void bindDefinition(Definition definition) {
        clickText = (Button) view.findViewById(R.id.clickText);
        wordTextView = (TextView) view.findViewById(R.id.wordTextView);
        image = (ImageView) view.findViewById(R.id.image);
        Glide.with(context)
                .load(definition.getImageUrl())
                .apply(new RequestOptions().override(MAX_WIDTH, MAX_HEIGHT).placeholder(R.drawable.pizza).error(R.drawable.pizza))
                .into(image);
        word = definition.getWord();
        wordTextView.setText(word);
        clickText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == clickText) {
            Intent intent = new Intent(context, DefinitionActivity.class);
            intent.putExtra("word", word);
            intent.putExtra("dictionary", "wiktionary");
            context.startActivity(intent);
        }
    }
//
//    private void addToSharedPreferences(Set<String> definitionSet) {
//        editor.putStringSet(Constants.PREFERENCES_DEFINITION_KEY, definitionSet).apply();
//    }
}