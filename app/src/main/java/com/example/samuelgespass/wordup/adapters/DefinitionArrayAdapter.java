package com.example.samuelgespass.wordup.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.samuelgespass.wordup.models.Definition;

/**
 * Created by samuelgespass on 3/16/18.
 */

public class DefinitionArrayAdapter extends ArrayAdapter {
    private Context context;
    private Definition[] definitions;

    public DefinitionArrayAdapter(Context context, int resource, Definition[] definitions) {
        super(context, resource);
        this.context = context;
        this.definitions = definitions;
    }

    @Override
    public Object getItem(int position) {
        Definition definition = definitions[position];
        return String.format("\n%s, %s. %s\n%s", definition.getWord(), definition.getPartOfSpeech(), definition.getDefinitionText(), definition.getAttributionText());
    }

    @Override
    public int getCount() {
        return definitions.length;
    }
}
