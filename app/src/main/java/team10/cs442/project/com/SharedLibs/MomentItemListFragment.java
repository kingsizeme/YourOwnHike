package team10.cs442.project.com.SharedLibs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.List;

import team10.cs442.project.com.R;
import team10.cs442.project.com.SharedLibs.dummy.DummyContent;

/**
 * A list fragment representing a list of Moment_Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link MomentItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class MomentItemListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentItemListFragment() {
    }

    public DummyContent.DummyItem new_item;
    public MyArrayAdapter myadapter;
    private String filter_trail = "none";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.
        int resID = android.R.layout.simple_list_item_activated_1;
        DummyContent.ITEMS.clear();
        myadapter = new MyArrayAdapter(getActivity(), resID, DummyContent.ITEMS);
        this.setListAdapter(myadapter);

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null) {
            if(extras.getString("from_act").equals("Pref_Act")){
                filter_trail = extras.getString("filter_trail");
            }
        }

        if(filter_trail.equals("none")){
            data_query();
        }
        else{
            filtered_data_query();
        }

    }

    public void filtered_data_query(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedMoments");
        query.whereExists("UserName");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> momentList, com.parse.ParseException e) {
                if (e == null) {
                    int cnt = 0;
                    Log.d("score", "Retrieved " + momentList.size() + " scores");
                    for (ParseObject momentObject : momentList) {
                        // use dealsObject.get('columnName') to access the properties of the Deals object
                        String tmp_loc = momentObject.get("Location").toString();
                        if(tmp_loc.equals(filter_trail)) {
                            cnt++;
                            String tmp_moment = momentObject.get("Moment").toString();
                            String tmp_username = momentObject.get("UserName").toString();
                            ParseFile myimage = momentObject.getParseFile("Image");
                            Log.v("test",tmp_username);
                            new_item = new DummyContent.DummyItem(Integer.toString(cnt), tmp_username, tmp_moment, myimage);
                            DummyContent.ITEMS.add(new_item);
                            DummyContent.ITEM_MAP.put(new_item.id, new_item);
                            myadapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    public void data_query(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("SharedMoments");
        query.whereExists("UserName");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> momentList, com.parse.ParseException e) {
                if (e == null) {
                    int cnt = 0;
                    Log.d("score", "Retrieved " + momentList.size() + " scores");
                    for (ParseObject momentObject : momentList) {
                        // use dealsObject.get('columnName') to access the properties of the Deals object
                        cnt++;
                        String tmp_moment = momentObject.get("Moment").toString();
                        String tmp_username = momentObject.get("UserName").toString();
                        ParseFile tmp_myimage = momentObject.getParseFile("Image");
                        Log.v("test",tmp_username);
                        new_item = new DummyContent.DummyItem(Integer.toString(cnt),tmp_username, tmp_moment, tmp_myimage);
                        DummyContent.ITEMS.add(new_item);
                        DummyContent.ITEM_MAP.put(new_item.id, new_item);
                        myadapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(DummyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
}
