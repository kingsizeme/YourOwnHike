package team10.cs442.project.com.Geo_Libs;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team10.cs442.project.com.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserEntryFragment extends Fragment {

    public UserEntryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_entry, container, false);
    }
}
