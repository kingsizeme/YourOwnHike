package team10.cs442.project.com.SharedLibs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;

import team10.cs442.project.com.R;
import team10.cs442.project.com.SharedLibs.dummy.DummyContent;

/**
 * A fragment representing a single Moment_Item detail screen.
 * This fragment is either contained in a {@link MomentItemListActivity}
 * in two-pane mode (on tablets) or a {@link MomentItemDetailActivity}
 * on handsets.
 */
public class MomentItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_momentitem_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            TextView nstext = (TextView) rootView.findViewById(R.id.stext);
            Log.v("content",mItem.toString());
            nstext.setText(mItem.toString());
            if(mItem.simage != null){
                ImageView nsimage = (ImageView)rootView.findViewById(R.id.simageview);
                loadImages(mItem.simage, nsimage);
            }
        }

        return rootView;
    }

    private void loadImages(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                        img.setImageBitmap(bmp);
                    } else {
                    }
                }
            });
        }
    }
}
