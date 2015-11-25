package team10.cs442.project.com.Animal_Libs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import team10.cs442.project.com.Animal_Libs.dummy.DummyContentAnimal;
import team10.cs442.project.com.Plant_Libs.dummy.DummyContentPlant;
import team10.cs442.project.com.Animal_Libs.MyAnimalAdapter;
import team10.cs442.project.com.Trail_Detail.TrailsItemsListActivity;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link AnimalItemsDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class AnimalItemsListFragment extends ListFragment {

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
    public AnimalItemsListFragment() {
    }

    public DummyContentAnimal.DummyItem new_item;
    public MyAnimalAdapter myadapter;
    private ListView lv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.
        int resID = android.R.layout.simple_list_item_activated_1;
        DummyContentAnimal.ITEMS.clear();
        myadapter = new MyAnimalAdapter(getActivity(), resID, DummyContentAnimal.ITEMS);
        this.setListAdapter(myadapter);
        data_query();
    }

    public void data_query(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Animals");
        query.whereExists("AnimalName");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> momentList, com.parse.ParseException e) {
                if (e == null) {
                    int cnt = 0;
                    for (ParseObject momentObject : momentList) {
                        // use dealsObject.get('columnName') to access the properties of the Deals object
                        cnt++;
                        String tmp_animal_name = momentObject.get("AnimalName").toString();
                        String tmp_animal_brief = momentObject.get("AnimalBrief").toString();
                        String tmp_animal_detail = momentObject.get("AnimalDetail").toString();
                        String[] array = new String[momentObject.getList("OccTrails").size()];
                        for(int i = 0; i < momentObject.getList("OccTrails").size(); i++){
                            array[i] = momentObject.getList("OccTrails").get(i).toString();
                        }

                        new_item = new DummyContentAnimal.DummyItem(Integer.toString(cnt), tmp_animal_name ,tmp_animal_brief, tmp_animal_detail, array);
                        DummyContentAnimal.ITEMS.add(new_item);
                        DummyContentAnimal.ITEM_MAP.put(new_item.id, new_item);
                        myadapter.notifyDataSetChanged();
                    }
                } else {
                    Log.d("Trail", "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = getListView();
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onC", "LongClickDetected");
                mCallbacks.onItemSelected(DummyContentAnimal.ITEMS.get(position).id);
                return true;
            }
        });
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
        //mCallbacks.onItemSelected(DummyContentGeo.ITEMS.get(position).id);
        Intent intent = new Intent(getContext(), TrailsItemsListActivity.class);
        intent.putExtra("trails_array", DummyContentAnimal.ITEMS.get(position).occur_trails);
        startActivity(intent);
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
