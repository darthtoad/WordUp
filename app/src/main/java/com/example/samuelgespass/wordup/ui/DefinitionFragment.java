package com.example.samuelgespass.wordup.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.DefinitionArrayAdapter;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.services.GiphyService;
import com.example.samuelgespass.wordup.services.WordnikService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefinitionFragment extends Fragment implements View.OnClickListener {
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

    String word;

    String dictionary;

    boolean bool = true;

    private ValueEventListener listener;
    DatabaseReference wordRef;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Set<String> definitionSet;

    public DefinitionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition, container, false);
        ButterKnife.bind(this, view);

        Intent wordIntent = getActivity().getIntent();
        word = wordIntent.getStringExtra("word");
        int startingPosition = getActivity().getIntent().getIntExtra("position", 0);
        dictionary = wordIntent.getStringExtra("dictionary");

        if (word == null || dictionary == null) {
            definitionSet = sharedPreferences.getStringSet("definition", new HashSet<String>());
            Object[] stringArr = definitionSet.toArray();
            word = stringArr[0].toString();
            dictionary = stringArr[1].toString();
            if (word.equals("wiktionary") || word.equals("webster") || word.equals("ahd") || word.equals("wordnet")) {
                word = stringArr[1].toString();
                dictionary = stringArr[0].toString();
            }
        }

        buttonFavorite.setOnClickListener(this);
        googleButton.setOnClickListener(this);
        etymologyButton.setOnClickListener(this);
        wikipediaButton.setOnClickListener(this);

        if (word != null) {
            if (dictionary == null) {
                dictionary = "wiktionary";
            }
            getDefinitions(word.trim().replaceAll("[^A-Za-z0-9 ]", ""), dictionary);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonFavorite) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            wordRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_WORDS)
                    .child(uid);
            listener = wordRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (word.equals(snapshot.child("word").getValue().toString())) {
                            if (i > 0) {
                                snapshot.getRef().removeValue();
                                bool = false;
                            }
                            i++;
                        }
                    }
                    if (bool) {
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Already saved word", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            DatabaseReference pushRef = wordRef.push();
            String pushId = pushRef.getKey();

            for (Definition definition : definitions) {
                definition.setImageUrl(imageUrl);
                definition.setPushId(pushId);
                pushRef.setValue(definition);
            }



        }

        if (view == googleButton) {
            String url = "https://www.google.com/search?q=" + word;
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(newIntent);
        }

        if (view == etymologyButton) {
            Intent newIntent = new Intent(getActivity(), EtymologyActivity.class);
            newIntent.putExtra("word", word);
            startActivity(newIntent);
        }
        if (view == wikipediaButton) {
            String url = "https://en.wikipedia.org/wiki/" + word;
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(newIntent);
        }
    }

    private void getDefinitions(final String word, final String dictionary) {
        final GiphyService giphyService = new GiphyService();
        giphyService.findImage(word, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                imageUrl = giphyService.processImageUrl(response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(getContext())
                                .load(imageUrl)
                                .into(imageView);
                    }
                });
            }
        });

        final WordnikService wordnikService = new WordnikService();
        wordnikService.findWord(word, dictionary, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                definitions = wordnikService.processDefinitionResults(response);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wordText.setText(word);
                        DefinitionArrayAdapter definitionArrayAdapter = new DefinitionArrayAdapter(getContext(), android.R.layout.simple_list_item_1, definitions.toArray(new Definition[definitions.size()]));
                        listView.setAdapter(definitionArrayAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            wordRef.removeEventListener(listener);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
