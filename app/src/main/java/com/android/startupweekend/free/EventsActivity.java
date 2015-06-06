package com.android.startupweekend.free;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class EventsActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    private String[] mDrawerItems;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mDrawerItems = getResources().getStringArray(R.array.drawer_items);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_events_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.activity_events_drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, mDrawerItems));
        mDrawerList.setItemChecked(4, true);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 200);
                } else if (position == 1) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), WhatsNewActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 200);
                } else if (position == 2) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), ProductsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 200);
                } else if (position == 3) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), ServicesActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 200);
                } else if (position == 5) {

                }
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.events_activity_fragment_container);

        if (fragment == null) {
            fragment = new PromoListFragment();
            fm.beginTransaction()
                    .add(R.id.events_activity_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
