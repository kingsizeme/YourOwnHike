package team10.cs442.project.com.Animal_Libs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team10.cs442.project.com.Animal_Libs.dummy.DummyContentAnimal;
import team10.cs442.project.com.R;

/**
 * Created by yukik_000 on 11/6/2015.
 */
public class MyAnimalAdapter extends ArrayAdapter<DummyContentAnimal.DummyItem> {

    private Context context;
    private List<DummyContentAnimal.DummyItem> tmp_objects;

    public MyAnimalAdapter(Context context, int _resource, List<DummyContentAnimal.DummyItem> objects) {
        super(context, R.layout.custom_ap_item_layout,objects );
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

        View rowView = inflater.inflate(R.layout.custom_ap_item_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.ap_list_item_label);
        TextView textView1 = (TextView) rowView.findViewById(R.id.ap_list_item_brief);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ap_list_item_logo);
        textView.setText(tmp_objects.get(position).mytoString());
        textView1.setText(tmp_objects.get(position).mytoBrieft());

        imageView.setImageResource(R.drawable.show_animal);

        rowView.setBackgroundColor(0x8bb3f0);

        return rowView;

    }
}
