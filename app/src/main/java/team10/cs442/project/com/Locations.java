package team10.cs442.project.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import team10.cs442.project.com.Animal_Libs.AnimalItemsListActivity;
import team10.cs442.project.com.Geo_Libs.Geo_Main_Act;
import team10.cs442.project.com.MainActs.MainAct;
import team10.cs442.project.com.Plant_Libs.PlantItemsListActivity;
import team10.cs442.project.com.tmp_Geo.GeoItemsListActivity;

public class Locations extends Activity {

    String user_saved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        final Button but_plant = (Button)findViewById(R.id.but_loc_plant);
        Button but_animal = (Button)findViewById(R.id.but_loc_animal);
        Button but_geoinfo = (Button)findViewById(R.id.but_loc_geo);
        final ImageView img_loc = (ImageView)findViewById(R.id.location_img);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            user_saved = extras.getString("user_name");
        }

        but_plant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_loc.setImageResource(R.drawable.plant_loc);
            }
        });
        but_plant.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(getBaseContext(), PlantItemsListActivity.class);
                intent.putExtra("user_name", user_saved);
                startActivity(intent);
                return false;
            }
        });
        but_animal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_loc.setImageResource(R.drawable.animal_loc);
            }
        });
        but_animal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(getBaseContext(), AnimalItemsListActivity.class);
                intent.putExtra("user_name", user_saved);
                startActivity(intent);
                return false;
            }
        });
        but_geoinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_loc.setImageResource(R.drawable.dl_582);
            }
        });
        but_geoinfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Intent intent = new Intent(getBaseContext(), GeoItemsListActivity.class);
                intent.putExtra("user_name", user_saved);
                startActivity(intent);
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gohome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(getBaseContext(), MainAct.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
