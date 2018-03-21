package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.ui.DefinitionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by samuelgespass on 3/16/18.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private Context context;
    private ArrayList<String> words;

    public FavoritesAdapter (Context context, ArrayList<String> words) {
        this.context = context;
        this.words = words;
    }

    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_item, parent, false);
        FavoritesViewHolder viewHolder = new FavoritesViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.FavoritesViewHolder holder, int position) {
        holder.bindWord(words.get(position));
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() { return words.size(); }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.wordTextView)
        TextView wordTextView;
        Context context;

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(this.context, DefinitionActivity.class);
            intent.putExtra("word", wordTextView.getText());
            context.startActivity(intent);
        }

        public FavoritesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            context = view.getContext();
            view.setOnClickListener(this);
        }

        public void bindWord(String word) {
            wordTextView.setText(word);
        }
    }
}
