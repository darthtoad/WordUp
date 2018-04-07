package com.example.samuelgespass.wordup.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DefinitionDetailFragment extends Fragment implements View.OnClickListener {

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

    private Definition definition;
    private ArrayList<Definition> definitions;
    private int position;

    public static DefinitionDetailFragment newInstance(ArrayList<Definition> definitions, Integer position) {
        DefinitionDetailFragment definitionDetailFragment = new DefinitionDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_KEY_DEFINITIONS, Parcels.wrap(definitions));
        args.putInt(Constants.EXTRA_KEY_POSITION, position);
        definitionDetailFragment.setArguments(args);
        return definitionDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        definitions = Parcels.unwrap(getArguments().getParcelable(Constants.EXTRA_KEY_DEFINITIONS));
        Log.e("defintions", definitions.toString() );
        position = getArguments().getInt(Constants.EXTRA_KEY_POSITION);
        definition = definitions.get(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definition_detail, container, false);
        ButterKnife.bind(this, view);

        Glide.with(getContext())
                .load(definition.getImageUrl())
                .into(imageView);

        wordText.setText(definition.getWord());
        DefinitionArrayAdapter definitionArrayAdapter = new DefinitionArrayAdapter(getContext(), android.R.layout.simple_list_item_1, definitions.toArray(new Definition[definitions.size()]));
        listView.setAdapter(definitionArrayAdapter);

        buttonFavorite.setOnClickListener(this);
        googleButton.setOnClickListener(this);
        etymologyButton.setOnClickListener(this);
        wikipediaButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == googleButton) {
            String url = "https://www.google.com/search?q=" + definition.getWord();
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(newIntent);
        }

        if (view == etymologyButton) {
            Intent newIntent = new Intent(getActivity(), EtymologyActivity.class);
            newIntent.putExtra("word", definition.getWord());
            startActivity(newIntent);
        }
        if (view == wikipediaButton) {
            String url = "https://en.wikipedia.org/wiki/" + definition.getWord();
            Intent newIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(url));
            startActivity(newIntent);
        }
    }
}
