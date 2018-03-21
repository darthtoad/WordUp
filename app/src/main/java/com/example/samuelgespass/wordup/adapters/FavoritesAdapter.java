package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.samuelgespass.wordup.R;

/**
 * Created by samuelgespass on 3/16/18.
 */

public class FavoritesAdapter extends BaseAdapter {
    private Context context;
    private String[] words;

    public FavoritesAdapter (Context context, String[] words) {
        this.context = context;
        this.words = words;
    }

    @Override
    public int getCount() {
        return words.length;
    }

    @Override
    public Object getItem(int i) {
        return words[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (view == null) {
            gridView = inflater.inflate(R.layout.favorites_grid_item, null);
            String word = this.words[i];

            TextView wordView = (TextView) gridView
                    .findViewById(R.id.grid_item_word);
            wordView.setText(this.getItem(i).toString());
        } else {
            gridView = (View) view;
        }

        return gridView;
    }
}
