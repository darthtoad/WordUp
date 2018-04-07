package com.example.samuelgespass.wordup.ui;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.samuelgespass.wordup.Constants;
import com.example.samuelgespass.wordup.R;
import com.example.samuelgespass.wordup.adapters.DefinitionPagerAdapter;
import com.example.samuelgespass.wordup.models.Definition;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DefinitionDetailActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private DefinitionPagerAdapter pagerAdapter;
    ArrayList<Definition> definitions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition_detail);
        ButterKnife.bind(this);
        definitions = Parcels.unwrap(getIntent().getParcelableExtra(Constants.EXTRA_KEY_DEFINITIONS));
        int startingPosition = getIntent().getIntExtra(Constants.EXTRA_KEY_POSITION, 0);

        pagerAdapter = new DefinitionPagerAdapter(getSupportFragmentManager(), definitions);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(startingPosition);
    }

}
