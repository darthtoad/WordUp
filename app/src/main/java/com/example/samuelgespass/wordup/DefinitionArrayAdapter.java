package com.example.samuelgespass.wordup;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by samuelgespass on 3/16/18.
 */

public class DefinitionArrayAdapter extends ArrayAdapter {
    private Context context;
    private String[] dictionaries;
    private String[] definitions;

    public DefinitionArrayAdapter(Context context, int resource, String[] dictionaries, String[] definitions) {
        super(context, resource);
        this.context = context;
        this.dictionaries = dictionaries;
        this.definitions = definitions;
    }

    @Override
    public Object getItem(int position) {
        String dictionary = dictionaries[position];
        String definition = definitions[position];
        return String.format("Dictionary: %s\n Definition:\n %s", dictionary, definition);
    }

    @Override
    public int getCount() {
        return definitions.length;
    }
}
