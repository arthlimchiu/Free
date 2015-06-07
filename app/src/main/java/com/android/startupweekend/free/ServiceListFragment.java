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
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.startupweekend.free.contentprovider.FreeContentProvider;
import com.android.startupweekend.free.database.PromoItemTable;
import com.gc.materialdesign.views.ButtonFlat;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "ServicesListFragment";

    private static final int SERVICE_LIST_LOADER = 3;

    private ListView mListView;
    private ServiceListAdapter mAdapter;

    public ServiceListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("Services");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service_list, container, false);

        mListView = (ListView) v.findViewById(R.id.service_listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PromoDetailsFragment fragment = PromoDetailsFragment.newInstance(id);
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .replace(R.id.services_activity_fragment_container, fragment)
//                        .addToBackStack(null)
//                        .commit();

                Intent intent = new Intent(getActivity(), ServiceDetailActivity.class);
                intent.putExtra(ServiceDetailActivity.SERVICE_ID, id);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(SERVICE_LIST_LOADER, null, this);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{PromoItemTable.COLUMN_ID,
                PromoItemTable.COLUMN_IMAGE, PromoItemTable.COLUMN_CAPTION, PromoItemTable.COLUMN_DESCRIPTION};
        String selection = PromoItemTable.COLUMN_CATEGORY + "=? and " + PromoItemTable.COLUMN_QUANTITY + ">?";
        String[] selectionArgs = new String[]{"services", "0"};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), FreeContentProvider.CONTENT_URI_PROMOITEMS, projection, selection, selectionArgs, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter = new ServiceListAdapter(getActivity(), data);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListView.setAdapter(null);
    }

    class ServiceListAdapter extends CursorAdapter {

        public ServiceListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.service_list_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) v.findViewById(R.id.service_item_image);
            holder.caption = (TextView) v.findViewById(R.id.service_item_caption);
            holder.description = (TextView) v.findViewById(R.id.service_item_description);
            holder.avail = (ButtonFlat) v.findViewById(R.id.service_item_avail);

            v.setTag(holder);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder holder = (ViewHolder) view.getTag();

            int image = cursor.getInt(cursor.getColumnIndex(PromoItemTable.COLUMN_IMAGE));
            String caption = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_CAPTION));
            String description = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_DESCRIPTION));

            holder.image.setImageResource(image);
            holder.caption.setText(caption);
            holder.description.setText(description);
            holder.avail.setOnClickListener(new OnButtonClickListener(cursor.getLong(cursor.getColumnIndex(PromoItemTable.COLUMN_ID))));
        }

        class OnButtonClickListener implements View.OnClickListener {

            private long id;

            public OnButtonClickListener(long id) {
                super();
                this.id = id;
            }

            @Override
            public void onClick(View v) {
//                AvailedPromoFragment fragment = AvailedPromoFragment.newInstance(id);
//                getActivity().getSupportFragmentManager()
//                        .beginTransaction()
//                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                        .replace(R.id.services_activity_fragment_container, fragment)
//                        .addToBackStack(null)
//                        .commit();
                Intent intent = new Intent(getActivity(), ServiceDetailActivity.class);
                intent.putExtra(ServiceDetailActivity.SERVICE_ID, id);
                intent.putExtra(ServiceDetailActivity.ACCESS_TYPE, ServiceDetailActivity.QUICK_AVAIL);
                startActivity(intent);
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
