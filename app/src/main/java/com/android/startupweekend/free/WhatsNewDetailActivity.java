package com.android.startupweekend.free;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class WhatsNewDetailActivity extends ActionBarActivity {

    private static final String TAG = "WhatsNewDetailActivity";

    public static final String WHATSNEW_ID = "WHATSNEW_ID";

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_new_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getLongExtra(WHATSNEW_ID, -1);
        
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.whatsnew_detail_fragment_container);

        if (fragment == null) {
            fragment = PromoDetailsFragment.newInstance(id);
            fm.beginTransaction()
                    .add(R.id.whatsnew_detail_fragment_container, fragment)
                    .commit();
        }
    }
}
