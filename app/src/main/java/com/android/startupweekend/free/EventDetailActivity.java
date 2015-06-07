package com.android.startupweekend.free;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class EventDetailActivity extends ActionBarActivity {

    private static final String TAG = "EventDetailActivity";

    public static final String EVENT_ID = "EVENT_ID";

    public static final String ACCESS_TYPE = "ACCESS_TYPE";

    public static final int QUICK_AVAIL = 0;
    public static final int DETAILED_AVAIL = 1;

    private long id;
    private int accessType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getLongExtra(EVENT_ID, -1);
        accessType = intent.getIntExtra(ACCESS_TYPE, DETAILED_AVAIL);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.event_detail_fragment_container);

        if (fragment == null) {
            if (accessType == QUICK_AVAIL) {
                fragment = AvailedPromoFragment.newInstance(id);
            } else if (accessType == DETAILED_AVAIL){
                fragment = PromoDetailsFragment.newInstance(id);
            }
            fm.beginTransaction()
                    .add(R.id.event_detail_fragment_container, fragment)
                    .commit();
        }
    }
}
