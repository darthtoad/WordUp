package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.ui.DefinitionDetailActivity;
import com.example.samuelgespass.wordup.ui.DefinitionDetailFragment;
import com.example.samuelgespass.wordup.util.OnDefintionSelectedListener;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by samuelgespass on 4/7/18.
 */

public class DefinitionListAdapter extends RecyclerView.Adapter<DefinitionListAdapter.DefinitionViewHolder> {
    private static final int MAX_WIDTH = 100;
    private static final int MAX_HEIGHT = 100;

    ArrayList<Definition> definitions = new ArrayList<>();
    private Context context;
    private OnDefintionSelectedListener onDefintionSelectedListener;

    public DefinitionListAdapter(Context context, ArrayList<Definition> definitions, OnDefintionSelectedListener defintionSelectedListener) {
        this.context = context;
        this.definitions = definitions;
        this.onDefintionSelectedListener = defintionSelectedListener;
    }

    @Override
    public DefinitionListAdapter.DefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorites_list_item, parent, false);
        DefinitionViewHolder viewHolder = new DefinitionViewHolder(view, definitions, onDefintionSelectedListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefinitionListAdapter.DefinitionViewHolder holder, int position) {
        holder.bindDefinition(definitions.get(position));
    }

    @Override
    public int getItemCount() { return definitions.size(); }

    public class DefinitionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.clickText)
        Button clickText;

        @BindView(R.id.wordTextView)
        TextView wordTextView;

        @BindView(R.id.image)
        ImageView imageView;

        private Context context;
        private int orientation;
        private ArrayList<Definition> definitions;
        public String word;
        private OnDefintionSelectedListener onDefintionSelectedListener;

        public DefinitionViewHolder(View itemView, ArrayList<Definition> definitions, OnDefintionSelectedListener defintionSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = itemView.getContext();
            this.orientation = itemView.getResources().getConfiguration().orientation;
            this.definitions = definitions;
            this.onDefintionSelectedListener = defintionSelectedListener;
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDefinitionDetailFragment(0);
            }
            itemView.setOnClickListener(this);
        }
        private void createDefinitionDetailFragment(int position) {
            DefinitionDetailFragment definitionDetailFragment = DefinitionDetailFragment.newInstance(definitions, position);
            android.support.v4.app.FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.definitionContainer, definitionDetailFragment);
            fragmentTransaction.commit();
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            onDefintionSelectedListener.onDefinitionSelected(itemPosition, definitions);
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                createDefinitionDetailFragment(itemPosition);
            } else {
                Intent intent = new Intent(context, DefinitionDetailActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_POSITION, itemPosition);
                intent.putExtra(Constants.EXTRA_KEY_DEFINITIONS, Parcels.wrap(definitions));
                context.startActivity(intent);
            }
        }

        public void bindDefinition(Definition definition) {
            Glide.with(context)
                    .load(definition.getImageUrl())
                    .apply(new RequestOptions().override(MAX_WIDTH, MAX_HEIGHT).placeholder(R.drawable.pizza).error(R.drawable.pizza))
                    .into(imageView);
            word = definition.getWord();
            wordTextView.setText(word);
            clickText.setOnClickListener(this);
        }
    }
}
