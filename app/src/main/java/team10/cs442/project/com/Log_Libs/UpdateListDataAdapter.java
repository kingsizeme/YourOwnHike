package team10.cs442.project.com.Log_Libs;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team10.cs442.project.com.R;
import team10.cs442.project.com.database_libs.DataProvider;
import team10.cs442.project.com.database_libs.Databasehelper;

/**
 * Created by yukik_000 on 11/8/2015.
 */
public class UpdateListDataAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public UpdateListDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler {
        TextView name, price, details, id;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutHandler lh;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.raw_layout, parent, false);
            lh = new LayoutHandler();
            lh.name = (TextView) row.findViewById(R.id.textView4);
            lh.id = (TextView) row.findViewById(R.id.textView5);
            row.setTag(lh);
        } else {
            lh = (LayoutHandler) row.getTag();
        }
        DataProvider dpp = (DataProvider) this.getItem(position);
        Log.d("In Adap", String.valueOf(dpp.getId()));
        lh.name.setText(dpp.getName());
        lh.id.setText(String.valueOf(dpp.getId()));

        LayoutInflater inf;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataProvider dpp1 = (DataProvider)getItem(position);
                String id = String.valueOf(dpp1.getId());
                Databasehelper DB = new Databasehelper(getContext());
                Toast.makeText(getContext(), "Selected Log for Update is " + id, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(),UpdateItemID.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("ID", id);
                getContext().startActivity(intent);
                notifyDataSetChanged();
            }
        });
        // notifyDataSetInvalidated();
        return row;
    }
}