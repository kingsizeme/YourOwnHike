package team10.cs442.project.com.SharedLibs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team10.cs442.project.com.R;
import team10.cs442.project.com.SharedLibs.dummy.DummyContent;

/**
 * Created by yukik_000 on 11/5/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<DummyContent.DummyItem> {

    private Context context;
    private List<DummyContent.DummyItem> tmp_objects;

    public MyArrayAdapter(Context context, int _resource, List<DummyContent.DummyItem> objects) {
        super(context, R.layout.custom_list_layout,objects );
        this.context = context;
        tmp_objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //View DummyView = super.getView(position,convertView, parent);

        //int color = 0x5108FF02;
        //DummyView.setBackgroundColor(color);
       // DummyView.setBackgroundResource(R.drawable.listshape);
        //DummyView.setBackgroundColor(color);
        //return DummyView;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_list_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.list_item_label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.list_item_logo);
        String Name = tmp_objects.get(position).mytoString();
        Log.v("adapter", Name);
        textView.setText(Name);

        imageView.setImageResource(R.drawable.moment);

        rowView.setBackgroundColor(0xffffffff);

        return rowView;

    }
}
