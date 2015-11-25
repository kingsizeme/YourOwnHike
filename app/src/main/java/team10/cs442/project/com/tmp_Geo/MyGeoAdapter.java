package team10.cs442.project.com.tmp_Geo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team10.cs442.project.com.Animal_Libs.dummy.DummyContentAnimal;
import team10.cs442.project.com.R;
import team10.cs442.project.com.tmp_Geo.dummy.DummyContentGeo;

/**
 * Created by yukik_000 on 11/6/2015.
 */
public class MyGeoAdapter extends ArrayAdapter<DummyContentGeo.DummyItem> {

    private Context context;
    private List<DummyContentGeo.DummyItem> tmp_objects;

    public MyGeoAdapter(Context context, int _resource, List<DummyContentGeo.DummyItem> objects) {
        super(context, R.layout.custom_list_layout,objects );
        this.context = context;
        this.tmp_objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //View DummyView = super.getView(position,convertView, parent);

        //int color = 0x5108FF02;
        //DummyView.setBackgroundColor(color);
        //DummyView.setBackgroundResource(R.drawable.listshape);
        //DummyView.setBackgroundColor(color);
        //return DummyView;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_list_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.list_item_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_logo);

        String tmp = tmp_objects.get(position).mytoString();
        Log.v("adapter", tmp);
        textView.setText(tmp);

        imageView.setImageResource(R.drawable.show_trail);

        rowView.setBackgroundColor(0x8bb3f0);

        return rowView;

    }
}
