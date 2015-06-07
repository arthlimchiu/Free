package com.android.startupweekend.free;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class ServiceDetailActivity extends ActionBarActivity {

    private static final String TAG = "ServiceDetailActivity";

    public static final String SERVICE_ID = "SERVICE_ID";

    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getLongExtra(SERVICE_ID, -1);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.service_detail_fragment_container);

        if (fragment == null) {
            fragment = PromoDetailsFragment.newInstance(id);
            fm.beginTransaction()
                    .add(R.id.service_detail_fragment_container, fragment)
                    .commit();
        }
    }

}
