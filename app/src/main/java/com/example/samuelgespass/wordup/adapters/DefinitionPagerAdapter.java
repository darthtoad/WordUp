package com.example.samuelgespass.wordup.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.samuelgespass.wordup.models.Definition;
import com.example.samuelgespass.wordup.ui.DefinitionDetailFragment;
import com.example.samuelgespass.wordup.ui.DefinitionFragment;

import java.util.ArrayList;

/**
 * Created by samuelgespass on 4/6/18.
 */

public class DefinitionPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Definition> definitions;

    public DefinitionPagerAdapter(FragmentManager fragmentManager, ArrayList<Definition> definitions) {
        super(fragmentManager);
        this.definitions = definitions;
    }

    @Override
    public Fragment getItem(int position) {
        return DefinitionDetailFragment.newInstance(definitions, position);
    }

    @Override
    public int getCount() { return definitions.size(); }

    @Override
    public CharSequence getPageTitle(int position) { return definitions.get(position).getWord(); }
}
