package com.android.startupweekend.free;

import android.content.ContentValues;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.startupweekend.free.contentprovider.FreeContentProvider;
import com.android.startupweekend.free.database.PromoItemTable;


public class MainActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;

    private String[] mDrawerItems;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mDrawerItems = getResources().getStringArray(R.array.drawer_items);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.activity_main_drawer_list);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_item, mDrawerItems));
        mDrawerList.setItemChecked(0, true);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
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
                } else if (position == 4) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getBaseContext(), EventsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 200);
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
        Fragment fragment = fm.findFragmentById(R.id.main_activity_fragment_container);

        if (fragment == null) {
            fragment = new PromoListFragment();
            fm.beginTransaction()
                    .add(R.id.main_activity_fragment_container, fragment)
                    .commit();
        }

        insertItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    private void insertItems() {
        ContentValues cv = new ContentValues();
        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.dreamproject);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Dream Project");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "THE DREAM PROJECT PH, a non-profit, volunteer-based community initiative which helps build the nation by shaping the dreams of the Filipino youth.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "events");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 25);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.innovationsummit);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Social Innovation Summit");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "The Social Innovation Summit is a twice annual event taking place in Washington, DC and Silicon Valley, that represents a global convening of black swans and wayward thinkers. Where most bring together luminaries to explore the next big idea, we bring together those hungry not just to talk about the next big thing, but to build it.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "events");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 50);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.oktoberfest);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Oktoberfest");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Oktoberfest is an annual event filled with music, revelry, beer, and good music.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "events");
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.startupweekend);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Startup Weekend Cebu 5");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Attend Startup Weekend Cebu 5 on June 5th, 2015 in Cebu. Learn to build a startup by being an entrepreneur!");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "events");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 50);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.frappe);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Starbucks Frappucino");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "First 120 persons to redeem gets to enjoy a Starbucks Frappucino worth Php 150 at Starbucks Colon St. Cebu City.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "products");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 120);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.pen);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Pen Giveaway");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "It's back to school for most of us and with that, Jollibee is giving away free pens to 250 lucky people.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "products");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 250);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.popcorn);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Popcorn");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "SM City Cebu is giving away 50 popcorn buckets in celebration of our Independece Day.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "products");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 50);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.shirt);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Globe Shirt Giveaway");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Globe is giving away 100 shirts to new subscribers.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "products");
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.sundae);
        cv.put(PromoItemTable.COLUMN_CAPTION, "McDonalds Sundae");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Click to redeem a McDonalds Sundae, only 250 redeem codes available.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "products");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 250);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.waterbottle);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Water Bottle");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Habagat is happy to give out 50 Aluminum Water Bottles, perfect for mountain trekking and outdoor activities.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "products");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 50);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.carwash);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Carwash");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Free carwash for the first 100 customers at Nice Day Carwash V.Rama Ave. Cebu City.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "services");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 100);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.dogvaccine);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Dog Vaccination");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Dog Vaccination/Heartworm shots for 500 dogs to be held at Lahug Baranggay Hall, Gorordo Ave. Cebu City.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "services");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 500);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.massage);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Massage");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Free 30 mins shiatsu massage at the newly opened Nuat Thai branch at Escario Street.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "services");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 30);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);

        cv.put(PromoItemTable.COLUMN_IMAGE, R.drawable.taxiride);
        cv.put(PromoItemTable.COLUMN_CAPTION, "Taxi Ride");
        cv.put(PromoItemTable.COLUMN_DESCRIPTION, "Free Taxi-ride for first 50 customers, must book app using MiCab: Your Hippest Personal Cab assistant.");
        cv.put(PromoItemTable.COLUMN_CATEGORY, "services");
        cv.put(PromoItemTable.COLUMN_QUANTITY, 50);
        getContentResolver().insert(FreeContentProvider.CONTENT_URI_PROMOITEMS, cv);
    }
}
