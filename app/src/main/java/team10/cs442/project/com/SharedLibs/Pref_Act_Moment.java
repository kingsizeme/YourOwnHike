package team10.cs442.project.com.SharedLibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import team10.cs442.project.com.R;

public class Pref_Act_Moment extends AppCompatActivity {

    private Spinner spinner1;
    private Button but_done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref__act__moment);

        getSupportActionBar().setTitle("Filter Preference");

        spinner1 = (Spinner) findViewById(R.id.spinner);

        but_done = (Button)findViewById(R.id.filter_done);

        but_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MomentItemListActivity.class);
                intent.putExtra("filter_trail",String.valueOf(spinner1.getSelectedItem()));
                intent.putExtra("from_act","Pref_Act");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pref__act__moment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
