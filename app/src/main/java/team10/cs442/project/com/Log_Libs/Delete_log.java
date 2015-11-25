package team10.cs442.project.com.Log_Libs;

import android.app.AlertDialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import team10.cs442.project.com.R;
import team10.cs442.project.com.database_libs.DataProvider;
import team10.cs442.project.com.database_libs.Databasehelper;

public class Delete_log extends AppCompatActivity {
    Databasehelper myDb;
    ListView listView;
    DeleteListDataAdapter listadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_log);
        listView = (ListView)findViewById(R.id.listView);
        listadapter = new DeleteListDataAdapter(getApplicationContext(),R.layout.raw_layout);
        listView.setAdapter(listadapter);
        myDb = new Databasehelper(this);
        Cursor res = myDb.viewAllData();
        if (res.getCount() == 0) {
            showMessage("No", "Data");
        }

        if(res.moveToFirst()){
            do{
                String name;
                int id;
                id = res.getInt(0);
                name = res.getString(1);
                Log.d("ID", String.valueOf(id));
                DataProvider dp = new DataProvider(id,name);
                listadapter.add(dp);
            }while(res.moveToNext());
        }
    }

    public void showMessage(String title,String message){
        Log.d(title, message);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}