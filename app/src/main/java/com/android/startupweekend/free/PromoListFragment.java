package com.android.startupweekend.free;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.startupweekend.free.contentprovider.FreeContentProvider;
import com.android.startupweekend.free.database.PromoItemTable;
import com.gc.materialdesign.views.ButtonFlat;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromoListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "PromoListFragment";

    private static final int PROMO_LIST_LOADER = 0;

    private ListView mListView;
    private PromoListAdapter mAdapter;


    public PromoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promo_list, container, false);

        mListView = (ListView) v.findViewById(R.id.promo_listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PromoDetailsFragment fragment = PromoDetailsFragment.newInstance(id);
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .replace(R.id.main_activity_fragment_container, fragment)
//                        .addToBackStack(null)
//                        .commit();

                Intent intent = new Intent(getActivity(), MainDetailActivity.class);
                intent.putExtra(MainDetailActivity.PROMO_ID, id);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(PROMO_LIST_LOADER, null, this);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{PromoItemTable.COLUMN_ID,
                PromoItemTable.COLUMN_IMAGE, PromoItemTable.COLUMN_CAPTION, PromoItemTable.COLUMN_DESCRIPTION};
        String selection = PromoItemTable.COLUMN_QUANTITY + ">?";
        String[] selectionArgs = new String[]{"0"};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), FreeContentProvider.CONTENT_URI_PROMOITEMS, projection, selection, selectionArgs, "_id desc");

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter = new PromoListAdapter(getActivity(), data);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListView.setAdapter(null);
    }

    class PromoListAdapter extends CursorAdapter {

        public PromoListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            View v = LayoutInflater.from(context).inflate(R.layout.promo_list_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) v.findViewById(R.id.promo_item_image);
            holder.caption = (TextView) v.findViewById(R.id.promo_item_caption);
            holder.description = (TextView) v.findViewById(R.id.promo_item_description);
            holder.avail = (ButtonFlat) v.findViewById(R.id.promo_item_avail);

            v.setTag(holder);
            return v;
        }

        @Override
        public void bindView(View view, final Context context, final Cursor cursor) {
            ViewHolder holder = (ViewHolder) view.getTag();

            int image = cursor.getInt(cursor.getColumnIndex(PromoItemTable.COLUMN_IMAGE));
            String caption = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_CAPTION));
            String description = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_DESCRIPTION));

            holder.image.setImageResource(image);
            holder.caption.setText(caption);
            holder.description.setText(description);
            holder.avail.setOnClickListener(new OnButtonClickListener(cursor.getInt(cursor.getColumnIndex(PromoItemTable.COLUMN_ID))));
        }

        class OnButtonClickListener implements View.OnClickListener {

            private int id;

            public OnButtonClickListener(int id) {
                super();
                this.id = id;
            }

            @Override
            public void onClick(View v) {
                AvailedPromoFragment fragment = AvailedPromoFragment.newInstance(id);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.main_activity_fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        class ViewHolder {
            ImageView image;
            TextView caption;
            TextView description;
            ButtonFlat avail;
        }
    }
}
