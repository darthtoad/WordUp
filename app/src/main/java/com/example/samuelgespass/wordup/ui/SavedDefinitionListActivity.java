package com.example.samuelgespass.wordup.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.FirebaseWordViewHolder;
import com.example.samuelgespass.wordup.models.Definition;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SavedDefinitionListActivity extends AppCompatActivity {
    private DatabaseReference definitionReference;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ButterKnife.bind(this);

        definitionReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_WORDS);
        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Definition, FirebaseWordViewHolder>(Definition.class, R.layout.favorites_list_item, FirebaseWordViewHolder.class, definitionReference) {
            @Override
            protected void populateViewHolder(FirebaseWordViewHolder viewHolder, Definition definition, int position) {
                viewHolder.bindWord(definition);
            }
        };
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseRecyclerAdapter.cleanup();
    }
}
