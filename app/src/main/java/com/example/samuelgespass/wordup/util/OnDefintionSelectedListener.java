package com.example.samuelgespass.wordup.util;

import com.example.samuelgespass.wordup.models.Definition;

import java.util.ArrayList;

/**
 * Created by samuelgespass on 4/6/18.
 */

public interface OnDefintionSelectedListener {
    public void onDefinitionSelected(Integer position, ArrayList<Definition> definitions);
}
