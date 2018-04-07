package com.example.samuelgespass.wordup.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.FirebaseDefinitionListAdapter;
import com.example.samuelgespass.wordup.adapters.FirebaseWordViewHolder;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.util.OnDefintionSelectedListener;
import com.example.samuelgespass.wordup.util.OnStartDragListener;
import com.example.samuelgespass.wordup.util.SimpleItemTouchHelperCallback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedDefinitionListActivity extends AppCompatActivity implements OnDefintionSelectedListener {
//    private DatabaseReference definitionReference;
    private FirebaseDefinitionListAdapter firebaseDefinitionListAdapter;
    private ItemTouchHelper helper;
    private Integer position;
    ArrayList<Definition> definitions;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_definition_list);
        if (savedInstanceState != null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                position = savedInstanceState.getInt(Constants.EXTRA_KEY_POSITION);
                definitions = Parcels.unwrap(savedInstanceState.getParcelable(Constants.EXTRA_KEY_DEFINITIONS));
            }

            if (position != null && definitions != null) {
                Intent intent = new Intent(this, DefinitionDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, position);
                intent.putExtra(Constants.EXTRA_KEY_DEFINITIONS, Parcels.wrap(definitions));
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (position != null && definitions != null) {
            outState.putInt(Constants.EXTRA_KEY_POSITION, position);
            outState.putParcelable(Constants.EXTRA_KEY_DEFINITIONS, Parcels.wrap(definitions));
        }
    }

    @Override
    public void onDefinitionSelected(Integer position, ArrayList<Definition> definitions) {
        this.position = position;
        this.definitions = definitions;
    }
}
