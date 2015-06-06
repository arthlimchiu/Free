package com.android.startupweekend.free;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.startupweekend.free.contentprovider.FreeContentProvider;
import com.android.startupweekend.free.database.PromoItemTable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "ProductListFragment";

    private static final int PRODUCT_LIST_LOADER = 2;

    private ListView mListView;
    private ProductListAdapter mAdapter;

    public ProductListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, container, false);

        mListView = (ListView) v.findViewById(R.id.product_listView);
        getLoaderManager().initLoader(PRODUCT_LIST_LOADER, null, this);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{PromoItemTable.COLUMN_ID,
                PromoItemTable.COLUMN_IMAGE, PromoItemTable.COLUMN_CAPTION, PromoItemTable.COLUMN_DESCRIPTION};
        CursorLoader cursorLoader = new CursorLoader(getActivity(), FreeContentProvider.CONTENT_URI_PROMOITEMS, projection, null, null, null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter = new ProductListAdapter(getActivity(), data);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListView.setAdapter(null);
    }

    class ProductListAdapter extends CursorAdapter {

        public ProductListAdapter(Context context, Cursor cursor) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View v = LayoutInflater.from(context).inflate(R.layout.product_list_item, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) v.findViewById(R.id.product_item_image);
            holder.caption = (TextView) v.findViewById(R.id.product_item_caption);
            holder.description = (TextView) v.findViewById(R.id.product_item_description);

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
        }

        class ViewHolder {
            ImageView image;
            TextView caption;
            TextView description;
        }
    }


}