package com.android.startupweekend.free;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.startupweekend.free.contentprovider.FreeContentProvider;
import com.android.startupweekend.free.database.PromoItemTable;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class AvailedPromoFragment extends Fragment {

    private static final String TAG = "AvailedPromoFragment";

    private static final String AVAILED_PROMO_ID = "AVAILED_PROMO_ID";

    private ImageView mImageView;
    private TextView mCaption, mDescription, mQuantity, mRedeemCode;

    private long id;

    private String[] characters = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
            "Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
            "p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    private Random random = new Random();

    public AvailedPromoFragment() {
        // Required empty public constructor
    }

    public static AvailedPromoFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(AVAILED_PROMO_ID, id);

        AvailedPromoFragment fragment = new AvailedPromoFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        id = args.getLong(AVAILED_PROMO_ID);

        getActivity().setTitle("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_availed_promo, container, false);

        mImageView = (ImageView) v.findViewById(R.id.availed_promo_image);
        mCaption = (TextView) v.findViewById(R.id.availed_promo_caption);
        mDescription = (TextView) v.findViewById(R.id.availed_promo_description);
        mQuantity = (TextView) v.findViewById(R.id.availed_promo_quantity);
        mRedeemCode = (TextView) v.findViewById(R.id.availed_promo_redeem_code);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        String[] projection = new String[]{PromoItemTable.COLUMN_IMAGE, PromoItemTable.COLUMN_CAPTION, PromoItemTable.COLUMN_DESCRIPTION,
                PromoItemTable.COLUMN_QUANTITY};
        Cursor cursor = getActivity().getContentResolver()
                .query(Uri.withAppendedPath(FreeContentProvider.CONTENT_URI_PROMOITEMS, String.valueOf(id)), projection, null, null, null);

        if (cursor.moveToFirst()) {
            int image = cursor.getInt(cursor.getColumnIndex(PromoItemTable.COLUMN_IMAGE));
            String caption = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_CAPTION));
            String description = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_DESCRIPTION));
            long quantity = cursor.getLong(cursor.getColumnIndex(PromoItemTable.COLUMN_QUANTITY));
            quantity -= 1;

            mImageView.setImageResource(image);
            mCaption.setText(caption);
            mDescription.setText(description);
            mQuantity.setText("Quantity: " + quantity);

            ContentValues cv = new ContentValues();
            cv.put(PromoItemTable.COLUMN_QUANTITY, quantity);
            getActivity().getContentResolver().update(Uri.withAppendedPath(FreeContentProvider.CONTENT_URI_PROMOITEMS, String.valueOf(id)), cv, null, null);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                int n = random.nextInt(62);
                builder.append(characters[n]);
            }

            mRedeemCode.setText(builder.toString());
        }
    }
}
