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
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class PromoDetailsFragment extends Fragment {

    private static final String TAG = "PromoDetailsFragment";

    public static final String PROMO_ID = "PROMO_ID";

    private ImageView mImageView;
    private TextView mCaption, mDescription, mQuantity, mRedeemCode;
    private ButtonRectangle mAvail;


    private long id, quantity;

    private String[] characters = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P",
            "Q","R","S","T","U","V","W","X","Y","Z","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o",
            "p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    private Random random = new Random();

    public PromoDetailsFragment() {
        // Required empty public constructor
    }

    public static PromoDetailsFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(PROMO_ID, id);

        PromoDetailsFragment fragment = new PromoDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        id = args.getLong(PROMO_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promo_details, container, false);

        mImageView = (ImageView) v.findViewById(R.id.promo_details_image);
        mCaption = (TextView) v.findViewById(R.id.promo_details_caption);
        mDescription = (TextView) v.findViewById(R.id.promo_details_description);
        mQuantity = (TextView) v.findViewById(R.id.promo_details_quantity);
        mRedeemCode = (TextView) v.findViewById(R.id.promo_details_redeem_code);
        mAvail = (ButtonRectangle) v.findViewById(R.id.promo_details_avail_button);
        mAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAvail.setBackgroundColor(getResources().getColor(R.color.ColorAvailed));

                quantity -= 1;
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
                mAvail.setEnabled(false);

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        String[] projection = new String[]{PromoItemTable.COLUMN_IMAGE, PromoItemTable.COLUMN_CAPTION, PromoItemTable.COLUMN_DESCRIPTION, PromoItemTable.COLUMN_QUANTITY};
        Cursor cursor = getActivity().getContentResolver()
                .query(Uri.withAppendedPath(FreeContentProvider.CONTENT_URI_PROMOITEMS, String.valueOf(id)), projection, null, null, null);

        if (cursor.moveToFirst()) {
            int image = cursor.getInt(cursor.getColumnIndex(PromoItemTable.COLUMN_IMAGE));
            String caption = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_CAPTION));
            String description = cursor.getString(cursor.getColumnIndex(PromoItemTable.COLUMN_DESCRIPTION));
            quantity = cursor.getLong(cursor.getColumnIndex(PromoItemTable.COLUMN_QUANTITY));

            mImageView.setImageResource(image);
            mCaption.setText(caption);
            mDescription.setText(description);
            mQuantity.setText("Quantity: " + quantity);
        }
    }
}
