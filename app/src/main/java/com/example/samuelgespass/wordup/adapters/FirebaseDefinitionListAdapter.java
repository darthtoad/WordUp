package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.ui.DefinitionActivity;
import com.example.samuelgespass.wordup.util.ItemTouchHelperAdapter;
import com.example.samuelgespass.wordup.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Guest on 4/4/18.
 */

public class FirebaseDefinitionListAdapter extends FirebaseRecyclerAdapter<Definition, FirebaseWordViewHolder> implements ItemTouchHelperAdapter {
    private DatabaseReference databaseReference;
    private OnStartDragListener dragListener;
    private Context context;
    private ChildEventListener listener;
    private ValueEventListener valueEventListener;
    private ArrayList<Definition> definitions = new ArrayList<>();

    public FirebaseDefinitionListAdapter(Class<Definition> modelClass, int modelLayout, Class<FirebaseWordViewHolder> viewHolderClass, Query query, OnStartDragListener dragListener, Context context) {
        super(modelClass, modelLayout, viewHolderClass, query);
        this.databaseReference = query.getRef();
        this.dragListener = dragListener;
        this.context = context;
        listener = databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!dataSnapshot.getValue(Definition.class).getWord().equals("")) {
                    definitions.add(dataSnapshot.getValue(Definition.class));
                    Log.e("Initial Defintions", definitions.toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseWordViewHolder viewHolder, Definition model, int position) {
        viewHolder.bindWord(model);
        viewHolder.image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    dragListener.onStartDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int from, int to) {
        Collections.swap(definitions, from, to);
        notifyItemMoved(from, to);
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        definitions.remove(position);
        getRef(position).removeValue();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        setIndexInFirebase();
        databaseReference.removeEventListener(listener);
    }

    private void setIndexInFirebase() {
//        try {
            for (Definition definition : definitions) {
                int index = definitions.indexOf(definition);
                DatabaseReference reference = getRef(index);
                definition.setIndex(Integer.toString(index));
                reference.setValue(definition);
            }
//        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
//        }
    }

}
