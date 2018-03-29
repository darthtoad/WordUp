package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.ui.FavoritesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Guest on 3/29/18.
 */

public class FirebaseWordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    @BindView(R.id.wordTextView)
    TextView wordTextView;

    View view;
    Context context;

    public FirebaseWordViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        context = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindWord(Definition definition) {
        wordTextView.setText(definition.getWord());
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Definition> definitions = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_WORDS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    definitions.add(snapshot.getValue(Definition.class));
                }

                int position = getLayoutPosition();

                Intent intent = new Intent(context, FavoritesActivity.class);
                intent.putExtra("position", position + "");
                intent.putExtra("definitions", Parcels.wrap(definitions));

                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
