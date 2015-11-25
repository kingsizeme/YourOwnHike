package team10.cs442.project.com;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import team10.cs442.project.com.Log_Libs.Add_Log;
import team10.cs442.project.com.Log_Libs.Delete_log;
import team10.cs442.project.com.Log_Libs.ListDataAdapter;
import team10.cs442.project.com.Log_Libs.Update_log;
import team10.cs442.project.com.MainActs.MainAct;
import team10.cs442.project.com.database_libs.DataProvider;
import team10.cs442.project.com.database_libs.Databasehelper;

public class MyLog extends Activity {
    Databasehelper myDb;
    static int k = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylogs);
        if (k==0){
            init();
            k++;
        }


        ListView listView = (ListView)findViewById(R.id.listView);
        ListDataAdapter listadapter = new ListDataAdapter(getApplicationContext(),R.layout.raw_layout);
        listView.setAdapter(listadapter);
        myDb = new Databasehelper(this);
        Cursor res = myDb.viewAllData();
        if (res.getCount() == 0) {
            Log.d("Error","Data not Found");
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
        }}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logs_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                newItem();
                return true;
            case R.id.update_id:
                updateItem();
                return true;
            case R.id.delete_id:
                deleteItem();
                return true;
            case R.id.home:
                Intent intent = new Intent(getBaseContext(), MainAct.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void init() {

        Databasehelper database = new Databasehelper(this);

        database.insertData("Log 1 ");
        database.insertData("Log 2 ");
        database.insertData("Log 3 ");

    }
    public void newItem(){
        Intent intent = new Intent(this, Add_Log.class);
        startActivity(intent);
    }
    public void deleteItem(){
        Intent intent = new Intent(this, Delete_log.class);
        startActivity(intent);
    }
    public void updateItem(){
        Intent intent = new Intent(this, Update_log.class);
        startActivity(intent);
    }
}
